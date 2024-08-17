import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from '../api/axios';
import '../styles/login-register.css';
import { ToastContainer, toast, Bounce } from "react-toastify";

const LOGIN_URL = '/students/login';

const Login = ( ) => {
    const [user, setUser] = useState('');
    const [pwd, setPwd] = useState('');
    const [errMsg, setErrMsg] = useState('');
    const [isAuthenticate,setIsAuthenticate]=useState(false);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(LOGIN_URL, 
                JSON.stringify({ email: user, password: pwd }), // Correct field name here
                {
                    headers: { 'Content-Type': 'application/json' },
                    withCredentials: true
                }

            );
            if (response.data) {
                localStorage.setItem("student", JSON.stringify(response.data));
                setIsAuthenticate(true);
                localStorage.setItem("isAuthenticate",isAuthenticate);
          
                navigate('/home');
                
            } else {
                setErrMsg('Login Failed');
            }
        } catch (err) {
            if (!err?.response) {
                setErrMsg('No Server Response');
            } else if (err.response?.status === 400) {
                setErrMsg('Missing Username or Password');
            } else if (err.response?.status === 401) {
                setErrMsg('Invalid Credentials');
            } else {
                setErrMsg('Login Failed');
            }
        }
    };
    return (
        <div className="form-container slide-in">
            <h1>Login</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor="username">Email:</label>
                <input
                    type="text"
                    id="username"
                    onChange={(e) => setUser(e.target.value)}
                    value={user}
                    required
                />
                
                <label htmlFor="password">Password:</label>
                <input
                    type="password"
                    id="password"
                    onChange={(e) => setPwd(e.target.value)}
                    value={pwd}
                    required
                />
                
                <button type="submit">Login</button>
                <p className="error-message">{errMsg}</p>
                <p>
                    If you don't have an account, <Link to="/register">Register Now</Link>
                </p>
            </form>
        </div>
    );
};

export default Login;
