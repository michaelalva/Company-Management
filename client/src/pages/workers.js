import { useEffect, useState } from 'react'
import ClickList from '../components/ClickList'
import { getWorkers, createWorker, getQualifications } from '../services/dataService'
import LocationID from '../utils/location'
import { pageStyle, pillButtonStyle } from '../utils/styles'

const Worker = ({ worker, active }) => {
    return (
        <div style={{ borderRadius: '8px' }}>
            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', padding: '12px 16px' }}>
                <div style={{ fontWeight: '500', fontSize: '17px', color: '#222' }}>
                    {worker.name}
                </div>
                <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                    <span style={{ fontSize: '12px', color: '#888' }}>
                        Workload: {worker.workload}
                    </span>
                    <span style={{ fontSize: '12px', color: '#888' }}>
                        ${worker.salary}
                    </span>
                </div>
            </div>
            {active === true && <WorkerBody worker={worker} />}
        </div>
    )
}

const WorkerBody = ({ worker }) => {
    return (
        <div style={{ background: '#f0f4f0', padding: '0 16px 40px 16px', display: 'flex', flexDirection: 'column', gap: '10px', position: 'relative' }}>
            <div>
                <div style={{ fontWeight: '600', fontSize: '13px', color: '#3d5c3c', marginBottom: '4px' }}>Projects</div>
                {worker.projects && worker.projects.length === 0
                    ? <div style={{ fontSize: '13px', color: '#aaa' }}>No projects assigned</div>
                    : <ClickList list={worker.projects || []} styles={{ background: '#f0f4f0' }} path="/projects" />
                }
            </div>
            <div>
                <div style={{ fontWeight: '600', fontSize: '13px', color: '#3d5c3c', marginBottom: '4px' }}>Qualifications</div>
                <div style={{ display: 'flex', flexWrap: 'wrap', gap: '6px' }}>
                    {(worker.qualifications || []).map(q => (
                        <span key={q} style={{
                            backgroundColor: '#4a7c59',
                            color: '#fff',
                            borderRadius: '50px',
                            padding: '4px 12px',
                            fontSize: '12px',
                            fontWeight: '500',
                        }}>
                            {q}
                        </span>
                    ))}
                </div>
            </div>
        </div>
    )
}

const workerRowStyle = {
    background: '#f0f4f0',
    borderRadius: '8px',
    marginBottom: '6px',
    fontFamily: "'Poppins', sans-serif",
    border: '3px solid #c8d5c8',
}


const Workers = () => {
    const [workers, setWorkers] = useState([])
    const [showForm, setShowForm] = useState(false)
    const [workerName, setWorkerName] = useState('')
    const [salary, setSalary] = useState('')
    const [qualifications, setQualifications] = useState([])
    const [selectedQualifications, setSelectedQualifications] = useState([])
    const [message, setMessage] = useState('')

    useEffect(() => { getWorkers().then(setWorkers) }, [])
    const active = LocationID('workers', workers, 'name')
    useEffect(() => { getQualifications().then(setQualifications) }, [])

    const handleSubmit = (e) => {
        e.preventDefault()

        if (workerName.trim() === '') { setMessage('Worker name is required'); return }
        if (salary === '' || isNaN(Number(salary))) { setMessage('Salary must be a valid number'); return }
        if (Number(salary) < 0) { setMessage('Salary cannot be negative'); return }
        if (selectedQualifications.length === 0) { setMessage('At least one qualification must be selected'); return }

        createWorker(workerName, selectedQualifications, Number(salary))
            .then(() => {
                setMessage('Worker created successfully!')
                setWorkerName('')
                setSelectedQualifications([])
                setSalary('')
                return getWorkers().then(setWorkers)
            })
            .catch(() => setMessage('Error creating worker. Please try again.'))
    }

    return (
        <div style={pageStyle}>
            <h1>Workers</h1>

            <button onClick={() => { setShowForm(!showForm); setMessage('') }} style={{ ...pillButtonStyle, color: '#fff' }}>
                {showForm ? '✕ Cancel' : '+ Create Worker'}
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
                        <label style={{ fontWeight: '600', marginRight: '8px' }}>Worker Name:</label>
                        <input
                            type="text"
                            value={workerName}
                            onChange={(e) => setWorkerName(e.target.value)}
                            style={{
                                padding: '6px 10px',
                                borderRadius: '4px',
                                border: '1px solid #888',
                                backgroundColor: '#f5f5f5',
                            }}
                        />
                    </div>

                    <div style={{ marginBottom: '12px' }}>
                        <label style={{ fontWeight: '600', marginRight: '8px' }}>Salary:</label>
                        <input
                            type="text"
                            value={salary}
                            onChange={(e) => setSalary(e.target.value)}
                            style={{
                                padding: '6px 10px',
                                borderRadius: '4px',
                                border: '1px solid #888',
                                backgroundColor: '#f5f5f5',
                            }}
                        />
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

            {workers.length === 0
                ? <p>No workers available</p>
                : <ClickList
                    active={active}
                    list={workers}
                    item={(w, a) => (<Worker worker={w} active={a} />)}
                    path='/workers'
                    id='name'
                    styles={workerRowStyle}
                />
            }
        </div>
    )
}

export default Workers
