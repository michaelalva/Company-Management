import { Nav, NavLink, NavLogo, NavMenu } from './navbarElements'

const Navbar = () => {
    return (
        <>
            <Nav>
                <NavLogo>
                    CSU - CS415
                </NavLogo>
                <NavMenu>
                    <NavLink to="/">
                        Home
                    </NavLink>
                    <NavLink to="/workers">
                        Workers
                    </NavLink>
                    <NavLink to="/projects">
                        Projects
                    </NavLink>
                    <NavLink to="/qualifications">
                        Qualifications
                    </NavLink>
                    <NavLink to="/about">
                        About
                    </NavLink>
                </NavMenu>
            </Nav>
        </>
    )
}

export default Navbar