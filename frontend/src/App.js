import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import ClientList from './components/ClientList';
import ClientEdit from "./components/ClientEdit";

class App extends Component {
  render() {
    return (
        <Router>
          <Switch>
            <Route path="/" exact component={Home} />
            <Route path='/clients' exact={true} component={ClientList}/>
            <Route path='/client/:id' component={ClientEdit}/>
            <Route path='/client' component={ClientEdit}/>
          </Switch>
        </Router>
    )
  }
}

export default App;