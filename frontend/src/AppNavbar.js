import React, { Component } from 'react';
import { Navbar, NavbarBrand, Nav, NavItem, NavLink } from 'reactstrap';
import { Link } from 'react-router-dom';

export default class AppNavbar extends Component {
    constructor(props) {
        super(props);
        this.state = { isOpen: false };
        this.toggle = this.toggle.bind(this);
    }

    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }

    render() {
        const brandStyle = {
            marginLeft: '0.9rem', // Adjust the margin as needed
        };

        return (
            <Navbar color="dark" dark expand="md">
                <NavbarBrand tag={Link} to="/" style={brandStyle}>Home</NavbarBrand>
                <Nav className="ml-auto" navbar>

                    <NavItem>
                        <NavLink tag={Link} to="/employees">Employees</NavLink>
                    </NavItem>
                </Nav>
            </Navbar>
        );
    }
}

