import { useEffect, useState } from 'react'
import ClickList from '../components/ClickList'
import { getQualifications, createQualification } from '../services/dataService'
import LocationID from '../utils/location'
import { pageStyle, pillButtonStyle } from '../utils/styles'

const Qualification = ({ qualification, active }) => {
    return (
        <div style={{ borderRadius: '8px' }}>
            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', padding: '12px 16px' }}>
                <div style={{ fontWeight: '500', fontSize: '17px', color: '#222' }}>
                    {qualification.description}
                </div>
                <span style={{ fontSize: '12px', color: '#888' }}>
                    {qualification.workers.length} worker{qualification.workers.length !== 1 ? 's' : ''}
                </span>
            </div>
            {active === true && <QualificationBody qualification={qualification} />}
        </div>
    )
}

const QualificationBody = ({ qualification }) => {
    return (
        <div style={{ background: '#f0f4f0', padding: '0 16px 16px 16px', display: 'flex', flexDirection: 'column', gap: '10px' }}>
            <div>
                <div style={{ fontWeight: '600', fontSize: '13px', color: '#3d5c3c', marginBottom: '4px' }}>Workers</div>
                {qualification.workers.length === 0
                    ? <div style={{ fontSize: '13px', color: '#aaa' }}>No workers have this qualification</div>
                    : <ClickList list={qualification.workers} styles={{ background: '#f0f4f0' }} path="/workers" />
                }
            </div>
        </div>
    )
}

const qualificationRowStyle = {
    background: '#f0f4f0',
    borderRadius: '8px',
    marginBottom: '6px',
    fontFamily: "'Poppins', sans-serif",
    border: '3px solid #c8d5c8',
}


const Qualifications = () => {
    const [qualifications, setQualifications] = useState([])
    const [newQualification, setNewQualification] = useState('')
    const [message, setMessage] = useState('')
    const [showForm, setShowForm] = useState(false)
    
    useEffect(() => { getQualifications().then(setQualifications) }, [])

    const addQualification = () => {
        if (newQualification.trim() === '') {
            setMessage('Qualification description is required')
            return
        }
        createQualification(newQualification)
            .then(() => {
                setMessage('Qualification created successfully')
                setNewQualification('')
                getQualifications().then(setQualifications)
            })
            .catch(() => {
                setMessage('Duplicate qualifications cannot be added. Please enter a new qualification.')
            })
    }

    const active = LocationID('qualifications', qualifications, 'description')

    return (
        <div style={pageStyle}>
            <h1>Qualifications</h1>

            <button onClick={() => { setShowForm(!showForm); setMessage('') }} style={{ ...pillButtonStyle, color: '#fff' }}>
                {showForm ? '✕ Cancel' : '+ Add Qualification'}
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
                        <label style={{ fontWeight: '600', marginRight: '8px' }}>Description:</label>
                        <input
                            type="text"
                            value={newQualification}
                            onChange={(e) => setNewQualification(e.target.value)}
                            style={{
                                padding: '6px 10px',
                                borderRadius: '4px',
                                border: '1px solid #888',
                                backgroundColor: '#f5f5f5',
                            }}
                        />
                    </div>

                    <button type="button" onClick={addQualification} style={{ ...pillButtonStyle, color: '#f0f4f0' }}>
                        + Submit
                    </button>

                    {message && <p style={{ marginTop: '8px', fontWeight: '600' }}>{message}</p>}
                </div>
            )}

            {qualifications.length === 0
                ? <p>No qualifications available</p>
                : <ClickList
                    active={active}
                    list={qualifications}
                    item={(q, a) => (<Qualification qualification={q} active={a} />)}
                    path='/qualifications'
                    id='description'
                    styles={qualificationRowStyle}
                />
            }
        </div>
    )
}

export default Qualifications