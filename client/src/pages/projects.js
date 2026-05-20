import { useEffect, useState } from 'react'
import ClickList from '../components/ClickList'
import { getProjects, createProject, getQualifications, startProject, finishProject, getWorkers, assignWorker, unassignWorker  } from '../services/dataService'
import LocationID from '../utils/location'
import { pageStyle, pillButtonStyle } from '../utils/styles'

const Project = ({ project, active, onRefresh }) => {
    const statusColors = {
        PLANNED:   { background: '#f5f3e8', color: '#7a7040' },
        ACTIVE:    { background: '#eaf6ea', color: '#2e7d32' },
        SUSPENDED: { background: '#fff3e0', color: '#8a5c00' },
        FINISHED:  { background: '#ececec', color: '#555' },
    }

    return (
        <div style={{ borderRadius: '8px' }}>
            {/* Collapsed row */}
            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', padding: '12px 16px' }}>
                <div style={{ fontWeight: '500', fontSize: '17px', color: '#222' }}>
                    {project.name}
                </div>
                <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                    <span style={{ fontSize: '12px', color: '#888', textTransform: 'uppercase', letterSpacing: '0.05em' }}>
                        {project.size.charAt(0) + project.size.slice(1).toLowerCase()}
                    </span>
                    <span style={{
                        fontSize: '15px',
                        fontWeight: '500',
                        padding: '2px 10px',
                        borderRadius: '50px',
                        ...statusColors[project.status],
                    }}>
                        {project.status.charAt(0) + project.status.slice(1).toLowerCase()}
                    </span>
                </div>
            </div>
            {/* Expanded details */}
            {active === true && (
                <ProjectBody project={project} onRefresh={onRefresh} />
            )}
        </div>
    )
}

const ProjectBody = ({ project, onRefresh }) => {
    const [allWorkers, setAllWorkers] = useState([]);
    const [selectedWorker, setSelectedWorker] = useState('');
    const [assignMessage, setAssignMessage] = useState('')

    useEffect(() => { getWorkers().then(setAllWorkers).catch(err => console.error(err)) }, [])

    const handleAssign = () => {
        if (!selectedWorker) return;
        assignWorker(selectedWorker, project.name)
            .then(() => {
                return getProjects().then(updatedProjects => {
                    const updated = updatedProjects.find(p => p.name === project.name)
                    if (updated && (updated.workers || []).includes(selectedWorker)) {
                        setAssignMessage('')
                        if (onRefresh) onRefresh()
                        setSelectedWorker('')
                    } else {
                        setAssignMessage('Worker could not be assigned. They may be overloaded or do not meet any missing qualifications.')
                    }
                })
            })
            .catch(() => setAssignMessage('Could not assign worker.'))
    }

    const handleUnassign = () => {
        if (!selectedWorker) return;
        unassignWorker(selectedWorker, project.name)
            .then(() => {
                if (onRefresh) onRefresh();
                setSelectedWorker('');
            })
            .catch(() => alert('Could not unassign worker.'));
    }

    const handleStart = () => {
        startProject(project.name)
            .then(() => { if (onRefresh) onRefresh() })
            .catch(() => alert('Could not start project.'))
    }
    const handleFinish = () => {
        finishProject(project.name)
            .then(() => { if (onRefresh) onRefresh() })
            .catch(() => alert('Could not finish project.'))
    }

    return (
        <div style={{ background: '#f0f4f0', padding: '0 16px 16px 16px', display: 'flex', flexDirection: 'column', gap: '10px', position: 'relative' }}>
                    {(project.status === 'PLANNED' || project.status === 'SUSPENDED') && (
                        <div>
                            <select 
                                value={selectedWorker} 
                                onClick={(e) => e.stopPropagation()} 
                                onChange={(e) => setSelectedWorker(e.target.value)}
                                style={{
                                    padding: '6px 12px',
                                    borderRadius: '50px',
                                    border: '1px solid #c8d5c8',
                                    backgroundColor: '#f0f4f0',
                                    fontFamily: "'Poppins', sans-serif",
                                    fontSize: '13px',
                                    cursor: 'pointer',
                                    outline: 'none',
                                    color: '#333',
                                }}
                            >
                                <option value="">Select Worker to Assign</option>
                                {allWorkers
                                    .filter(w => !(project.workers || []).includes(w.name))
                                    .map(w => <option key={w.name} value={w.name}>{w.name}</option>)
                                }
                            </select>
                            <button onClick={(e) => {e.stopPropagation(); handleAssign()}} style={{ ...pillButtonStyle, marginLeft: '5px', color: '#f0f4f0' }}>
                                Assign
                            </button>
                            {assignMessage && (
                                <p style={{ color: '#e57373', fontWeight: '600', marginTop: '6px', fontSize: '13px' }}>
                                    {assignMessage}
                                </p>
                            )}
                        </div>
                    )}
            <div>
                <div style={{ fontWeight: '600', fontSize: '13px', color: '#3d5c3c', marginBottom: '4px' }}>Workers</div>
                {project.workers && project.workers.length === 0
                    ? <div style={{ fontSize: '13px', color: '#aaa' }}>No workers assigned</div>
                    : <ClickList list={project.workers || []} styles={{ background: '#f0f4f0' }} path="/workers" />
                }
                {project.workers && project.workers.length > 0 && (
                    <div style={{ marginTop: '8px' }}>
                        <select
                            value={selectedWorker}
                            onClick={(e) => e.stopPropagation()}
                            onChange={(e) => setSelectedWorker(e.target.value)}
                            style={{
                                padding: '6px 12px',
                                borderRadius: '50px',
                                border: '1px solid #c8d5c8',
                                backgroundColor: '#f0f4f0',
                                fontFamily: "'Poppins', sans-serif",
                                fontSize: '13px',
                                cursor: 'pointer',
                                outline: 'none',
                                color: '#333',
                            }}
                        >
                            <option value="">Select Worker to Unassign</option>
                            {project.workers.map(w => (
                                <option key={w} value={w}>{w}</option>
                            ))}
                        </select>
                        <button
                            onClick={(e) => { e.stopPropagation(); handleUnassign() }}
                            style={{ ...pillButtonStyle, marginLeft: '5px', color: '#f0f4f0' }}
                        >
                            Unassign
                        </button>
                    </div>
                )}
            </div>
            <div>
                <div style={{ fontWeight: '600', fontSize: '13px', color: '#3d5c3c', marginBottom: '4px' }}>Qualifications</div>
                <div>
                    {(project.qualifications || []).map(q => (
                        <span key={q} style={{
                            display: 'inline-block',
                            margin: '3px',
                            padding: '3px 10px',
                            borderRadius: '12px',
                            backgroundColor: (project.missingQualifications || []).includes(q) ? '#e57373' : '#81c784',
                            color: '#fff',
                            fontWeight: '600',
                            fontSize: '13px',
                        }}>
                            {q}
                        </span>
                    ))}
                </div>
                </div>
                <div style={{ position: 'absolute', bottom: '16px', right: '16px' }}>
                    {(project.status === 'PLANNED' || project.status === 'SUSPENDED') && (
                        <button onClick={(e) => {e.stopPropagation(); handleStart()}} style={{ ...pillButtonStyle, marginTop: '0', color: '#f0f4f0' }}>
                            ▶ Start Project
                        </button>
                    )}
                    {project.status === 'ACTIVE' && (
                        <button onClick={(e) => {e.stopPropagation(); handleFinish()}} style={{ ...pillButtonStyle, marginTop: '0', color: '#f0f4f0' }}>
                            ✓ Finish Project
                        </button>
                    )}
                </div>
        </div>
    )
}

const projectRowStyle = {
    background: '#f0f4f0',
    borderRadius: '8px',
    marginBottom: '6px',
    fontFamily: "'Poppins', sans-serif",
    border: '3px solid #c8d5c8',
}

const Projects = () => {
    const [projects, setProjects] = useState([])
    const [showForm, setShowForm] = useState(false)
    const [projectName, setProjectName] = useState('')
    const [size, setSize] = useState('')
    const [qualifications, setQualifications] = useState([])
    const [selectedQualifications, setSelectedQualifications] = useState([])
    const [message, setMessage] = useState('')

    useEffect(() => { getProjects().then(setProjects) }, [])

    useEffect(() => {
        getQualifications().then(q => {
            console.log('qualifications:', q)
            setQualifications(q)
        })
    }, [])

    const active = LocationID('projects', projects, 'name')

    const handleSubmit = (e) => {
        e.preventDefault()
        const validSizes = ['SMALL', 'MEDIUM', 'BIG']

        if (projectName.trim() === '') {
            setMessage('Project name is required')
            return
        }

        if (!validSizes.includes(size)) {
            setMessage('Please select a project size')
            return
        }

        if (selectedQualifications.length === 0) {
            setMessage('At least one qualification must be selected')
            return
        }

        createProject(projectName, selectedQualifications, size)
            .then(() => {
                setMessage('Project created successfully!')
                setProjectName('')
                setSize('')
                setSelectedQualifications([])
                return getProjects().then(setProjects)
            })
            .catch(() => {
                setMessage('Error creating project. Please try again.')
            })
    }

    const refreshProjects = () => getProjects().then(setProjects)

    return (
        <div style={pageStyle}>
            <h1>
                Projects
            </h1>

            <button onClick={() => { setShowForm(!showForm); setMessage('') }} style={{ ...pillButtonStyle, color: '#fff' }}>
                {showForm ? '✕ Cancel' : '+ Create Project'}
            </button>

            {showForm && (
                <div style={{
                    backgroundColor: '#f0f4f0',
                    margin: '0.2vw',
                    padding: '1.5vw',
                    borderRadius: '8px',
                    marginBottom: '20px',
                    border: '3px solid #c8d5c8',
                }}>
                    <div style={{ marginBottom: '12px' }}>
                        <label style={{ fontWeight: '600', marginRight: '8px' }}>Project Name:</label>
                        <input
                            type="text"
                            value={projectName}
                            onChange={(e) => setProjectName(e.target.value)}
                            style={{
                                padding: '6px 10px',
                                borderRadius: '4px',
                                border: '1px solid #888',
                                backgroundColor: '#f5f5f5',
                            }}
                        />
                    </div>

                    <div style={{ marginBottom: '12px' }}>
                        <label style={{ fontWeight: '600', marginRight: '8px' }}>Size:</label>
                        <div style={{ marginTop: '8px', padding: '0 4px' }}>
                            <input
                                type="range"
                                min="0"
                                max="2"
                                step="1"
                                value={['SMALL', 'MEDIUM', 'BIG'].indexOf(size) === -1 ? 0 : ['SMALL', 'MEDIUM', 'BIG'].indexOf(size)}
                                onChange={(e) => setSize(['SMALL', 'MEDIUM', 'BIG'][e.target.value])}
                                style={{ width: '200px', accentColor: '#6b7a4f', cursor: 'pointer' }}
                            />
                            <div style={{
                                display: 'flex',
                                justifyContent: 'space-between',
                                width: '200px',
                                marginTop: '4px',
                            }}>
                                {['SMALL', 'MEDIUM', 'BIG'].map(s => (
                                    <span key={s} style={{
                                        fontWeight: size === s ? '700' : '400',
                                        color: size === s ? '#3b4a2f' : '#555',
                                        fontSize: '13px',
                                    }}>
                                        {s}
                                    </span>
                                ))}
                            </div>
                        </div>
                    </div>

                    <div style={{ marginBottom: '12px' }}>
                        <label style={{ fontWeight: '600' }}>Qualifications:</label>
                        <div style={{
                            backgroundColor: '#f0f4f0',
                            borderRadius: '4px',
                            padding: '10px',
                            marginTop: '6px',
                            display: 'flex',
                            flexWrap: 'wrap',
                            gap: '8px',
                        }}>
                            {qualifications.map(q => (
                                <label key={q.description} style={{
                                    border: '1px solid #aaa',
                                    borderRadius: '20px',
                                    padding: '4px 12px',
                                    cursor: 'pointer',
                                    fontWeight: selectedQualifications.includes(q.description) ? '700' : '400',
                                    backgroundColor: selectedQualifications.includes(q.description) ? '#6b7a4f' : '#f5f5f5',
                                    color: selectedQualifications.includes(q.description) ? '#fff' : '#000',
                                }}>
                                    <input
                                        type="checkbox"
                                        value={q.description}
                                        checked={selectedQualifications.includes(q.description)}
                                        onChange={(e) => {
                                            const value = e.target.value
                                            setSelectedQualifications(prev =>
                                                prev.includes(value)
                                                    ? prev.filter(q => q !== value)
                                                    : [...prev, value]
                                            )
                                        }}
                                        style={{ display: 'none' }}
                                    />
                                    {q.description}
                                </label>
                            ))}
                        </div>
                    </div>

                    <button type="button" onClick={handleSubmit} style={{ ...pillButtonStyle, color: '#f0f4f0' }}>
                        + Submit
                    </button>

                    {message && <p style={{ marginTop: '8px', fontWeight: '600' }}>{message}</p>}
                </div>
            )}

            {projects.length === 0
                ? <p>No projects available</p>
                : <ClickList 
                    active={active} 
                    list={projects} 
                    item={(p, a) => (<Project project={p} active={a} onRefresh={refreshProjects}/>)} 
                    path='/projects' 
                    id='name'
                    styles={projectRowStyle}
                />
            }
        </div>
    )
}

export default Projects