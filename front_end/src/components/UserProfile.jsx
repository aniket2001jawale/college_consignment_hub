import React, { useState, useEffect } from 'react';
import axios from '../api/axios';
import '../styles/UserProfile.css';
import { Link } from 'react-router-dom';

const UserProfile = () => {
  const [student, setStudent] = useState(null);
  const [formData, setFormData] = useState({
    student_name: '',
    username: '',
    email: '',
    address: '',
    password: '' // Add password field
  });
  const [errors, setErrors] = useState({});

  useEffect(() => {
    const storedStudent = JSON.parse(localStorage.getItem('student'));
    setStudent(storedStudent);

    if (storedStudent) {
      setFormData({
        student_id:storedStudent.student_id ,
        student_name: storedStudent.student_name || '',
        username: storedStudent.username || '',
        email: storedStudent.email || '',
        address: storedStudent.address || '',
        password: '' // Clear password field on load
      });
    }
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (student && student.student_id) {
        await axios.put(`/students/updatestudent/${student.student_id}`, formData, {
          headers: { 'Content-Type': 'application/json' },
          withCredentials: true
        });
        alert('Profile updated successfully!');
      } else {
        setErrors({ ...errors, update: 'Student ID is missing' });
      }
    } catch (error) {
      console.error('Failed to update profile', error);
      setErrors({ ...errors, update: 'Failed to update profile' });
    }
  };

  return (
    <div className="user-profile">
      <h2>Edit Your Profile</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="student_name">Name</label>
          <input
            type="text"
            id="student_name"
            name="student_name"
            value={formData.student_name}
            onChange={handleChange}
          />
        </div>
        <div className="form-group">
          <label htmlFor="username">Username</label>
          <input
            type="text"
            id="username"
            name="username"
            value={formData.username}
            onChange={handleChange}
          />
        </div>
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
        </div>
        <div className="form-group">
          <label htmlFor="address">Address</label>
          <textarea
            id="address"
            name="address"
            value={formData.address}
            onChange={handleChange}
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            required // Make sure the password field is required
          />
        </div>
        {errors.update && <div className="error">{errors.update}</div>}
        <div className="form-group">
          <button type="submit">Save Changes</button>
          <Link to='/home'>
            <button type="button" className='btn btn-success'>Back to HomePage</button>
          </Link>
        </div>
      </form>
    </div>
  );
};

export default UserProfile;
