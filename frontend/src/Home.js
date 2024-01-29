import React, { Component } from 'react';
import './App.css';
import AppNavbar from './components/AppNavbar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

class Home extends Component {
    render() {
        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <Button color="link"
                        onClick={() => {
                            this.props.history.push(`/clients`)
                            window.location.reload()}}>
                            Clients
                   </Button>
                </Container>
            </div>
        );
    }
}
export default Home;