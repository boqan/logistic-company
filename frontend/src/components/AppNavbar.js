import React, {Component} from 'react';
import {Navbar, NavbarBrand, NavItem, Nav, Container} from 'reactstrap';
import {Link} from 'react-router-dom';

export default class AppNavbar extends Component {
    constructor(props) {
        super(props);
        this.state = {isOpen: false};
        this.toggle = this.toggle.bind(this);
    }

    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }   

    render() {
        return (
            <Navbar color="dark" dark expand="md">
            <Container>
              <NavbarBrand tag={Link} to="/">
                Home
              </NavbarBrand>
              <NavbarBrand tag={Link} to="/clients">
                Clients
              </NavbarBrand>
              <NavbarBrand tag={Link} to="/orders">
                Orders
              </NavbarBrand>
            </Container>
          </Navbar>
        );
    }
}