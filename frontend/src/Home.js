import React from 'react';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';
import AppNavbar from './AppNavbar';

class Home extends React.Component {
  render() {
    return (
      <div>
        <AppNavbar />
        <Container fluid>
           <h1> Home page that is yet to be filled. </h1>
        </Container>
      </div>
    );
  }
}


export default Home;
