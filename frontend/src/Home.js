import React, { Component } from 'react';
import './App.css';
import AppNavbar from './components/AppNavbar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

class Home extends Component {
    render() {
        return (
            <div>
            <AppNavbar />
            <Container fluid>
              <h2>Welcome to the Home Page</h2>
            </Container>
          </div>
        );
    }
}
export default Home;