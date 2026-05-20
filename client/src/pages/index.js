import { useNavigate } from 'react-router-dom'
import { pageStyle, pillButtonStyle } from '../utils/styles'
import '@fontsource/dm-sans'

const Home = () => {
    const navigate = useNavigate()

    return (
        <div style={pageStyle}>
            <div style={{ textAlign: 'center' }}>
                <h1 style={{ fontFamily: "'DM Sans', sans-serif", fontWeight: '400', fontSize: '5vw' }}>
                    Company Management
                </h1>
                <br />
                <button style={{ ...pillButtonStyle, color: '#f0f4f0' }} onClick={() => navigate('/projects')}>
                    + Create Project
                </button>
                <br /><br /><br /> <br /><br /><br /> <br /><br /><br /> <br /><br />
                <h2>CS415 - Colorado State University</h2>
                <h2>Spring 2026</h2>
                <br />
            </div>
        </div>
    )
}

export default Home
