import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './connect/Login';
import AdminView from './insideView/admin-view';
import Signup from './connect/Signup'; // Import your Signup component
import UpdateCompany from './update/UpdateCompany';
import CompanyView from './insideView/company-view';
import EmployeeClientView from './insideView/office-view';
import CreateCompany from './connect/logistic-company';
import CreateOffice from './connect/office';
import UpdateOffice from './update/updateOffice';
import CreateEmployee from './connect/employee';
import UpdateEmployee from './update/updateEmployee';
import CreateClient from './connect/client';
import UpdateClient from './update/updateClient';
import CreateOrder from './connect/creaete-order';
import UpdateOrder from './update/updateOrder';
import ClientView from './insideView/client-view';
import EmployeeView from './insideView/employee-view';


function App() {
  return (
    <Router>
      <div className="app">
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/create-company" element={<CreateCompany />} /> {/* Add the signup route */}
          <Route path="/admin-view" element={<AdminView />} />
          <Route path="/office-view/:officeId" element={<EmployeeClientView />} />
          <Route path="/company-view/:companyId" element={<CompanyView />} />
          <Route path="/update-company/:id" element={<UpdateCompany />} />
          <Route path="/update-office/:id" element={<UpdateOffice />} />
          <Route path="/create-office/:companyId" element={<CreateOffice />} />
          <Route path="/add-employee/:officeId" element={<CreateEmployee />} />
          <Route path="/update-employee/:employeeId" element={<UpdateEmployee />} />
          <Route path="/add-client/:officeId" element={<CreateClient />} />
          <Route path="/update-client/:clientId" element={<UpdateClient />} />
          <Route path="/create-order/:officeId" element={<CreateOrder />} />
          <Route path="/update-order/:orderId" element={<UpdateOrder />} />
          <Route path="/client-view/:clientId" element={<ClientView />} />
          <Route path="/employee-view/:employeeId" element={<EmployeeView />} />
          
          {/* Redirect to /login if the route is not found or if the user tries to access an undefined route */}
          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
