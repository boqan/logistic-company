import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import ClientList from './components/ClientList';
import ClientEdit from "./components/ClientEdit";
import OrderList from "./components/OrderList";
import OrderEdit from "./components/OrderEdit";



class App extends Component {
  render() {
    return (
        <Router>
          <Switch>
            <Route path="/" exact component={Home} />
            <Route path='/clients' exact={true} component={ClientList}/>
            <Route path='/client/:id' component={ClientEdit}/>
            <Route path='/client' component={ClientEdit}/>
            <Route path='/orders' exact={true} component={OrderList}/>
            <Route path='/order/:id' component={OrderEdit}/>
            <Route path='/order' component={OrderEdit}/>
          </Switch>
        </Router>
    )
  }
}

export default App;