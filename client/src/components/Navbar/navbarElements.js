import { NavLink as Link } from 'react-router-dom'
import styled from 'styled-components'
import '@fontsource/poppins'

export const Nav = styled.nav`
  font-family: 'Poppins', sans-serif;
  background: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1vw 2vw;
`

export const NavMenu = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5vw;
  margin-left: auto;
`

export const NavLogo = styled.div`
  font-family: 'Poppins', sans-serif;
  color: #fff;
  font-size: 0.9vw;
  font-weight: 700;
  letter-spacing: 0.15em;
  text-transform: uppercase;
  padding: 0.5vw 1.2vw;
  white-space: nowrap;
  background: #3d5c3c;
  border-radius: 50px;
`

export const NavLink = styled(Link)`
  color: #555;
  font-size: 0.75vw;
  font-weight: 500;
  letter-spacing: 0.12em;
  text-transform: uppercase;
  text-decoration: none;
  padding: 0.5vw 1.2vw;
  display: flex;
  align-items: center;
  cursor: pointer;
  border-radius: 50px;
  transition: all 0.2s ease;
  &:hover {
    color: #fff;
    background: #3d5c3c;
  }
  &.active {
    color: #fff;
    font-weight: 700;
    background: #3d5c3c;
  }
`