import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import EmployeeAdd from './EmployeeAdd';
import EmployeeUpdate from './EmployeeUpdate';
import EmployeeDelete from './EmployeeDelete';

class App extends Component {
  render() {
    return (
     // Set up the Router for handling different routes
      <Router>
        <Switch>
         {/* Home Route */}
          <Route path="/" exact component={Home} />
          <Route path='/employees' exact={true} component={EmployeeDelete} />
          <Route path='/employee/updateByEmail/:email' component={EmployeeUpdate} />
          <Route path="/employee/new" component={EmployeeAdd} />
        </Switch>
      </Router>
    );
  }
}


export default App;
