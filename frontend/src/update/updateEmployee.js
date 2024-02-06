import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios'; // Make sure axios is imported

const EmployeeUpdate = () => {
    const { employeeId } = useParams();
    const navigate = useNavigate();
    const [employeeData, setEmployeeData] = useState({  name: '',
    salary: '',
    employeeType: '',
    officeID:''
});
    const token = localStorage.getItem('token');
 


useEffect(() => {
    const fetchCompanyData = async () => {
        try {
        const response = await axios.get(`http://localhost:8082/employee/findById/${employeeId}`, {
            headers: { Authorization: `Bearer ${token}` }
        });
        setEmployeeData({
            name: response.data.name,
            salary: response.data.salary,
            employeeType: response.data.employeeType,
            officeID: response.data.officeID
          });
        } catch (error) {
        console.error('Error fetching company data:', error);
        // Handle error accordingly
        }
    };

    fetchCompanyData();
    }, [employeeId, token]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setEmployeeData(prevData => ({ ...prevData, [name]: value }));
    };
    
  
  
  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      await axios.put(`http://localhost:8082/employee/update/${employeeId}`, employeeData,  {
        headers: { Authorization: `Bearer ${token}` }
      });
      navigate(-1); // Redirect to profile or confirmation page
    } catch (error) {
      console.error('Error updating company:', error);
      // Handle error accordingly
    }
  };

  return (
    <div>
      <h1>Update Employee</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="name">Name:</label>
          <input
                type="text"
                id="name" // Changed from "name" to "address" to match the state property
                name="name" // Changed from "name" to "address" to match the state property
                value={employeeData.name}
                onChange={handleInputChange}
                required
            />
        </div>
        <div>
          <label htmlFor="name">Salary:</label>
          <input
                type="text"
                id="salary" // Changed from "name" to "address" to match the state property
                name="salary" // Changed from "name" to "address" to match the state property
                value={employeeData.salary}
                onChange={handleInputChange}
                required
            />
        </div>
        <div className="form-group">
                    <label htmlFor="role">Role:</label>
                    <select
                        id="role"
                        name="employeeType"
                        className="custom-dropdown"
                        value={employeeData.employeeType}
                        onChange={handleInputChange} 
                        style={{ fontSize: '1.25rem', padding: '0.5rem 1rem' }}
                        required
                    >
                        <option value="COURIER">Courier</option>
                        <option value="DRIVER">Driver</option>
                        <option value="LOADER">Loader</option>
                        <option value="ACCOUNTANT">Accountant</option>
                        <option value="ADMINISTRATOR">Administrator</option>
                        
                    </select>
            </div>
        <button type="submit">Submit Changes</button>
      </form>
    </div>
  );

}
export default EmployeeUpdate;
