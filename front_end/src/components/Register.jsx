
import React, { useRef, useState, useEffect } from 'react';
import { faCheck, faTimes, faInfoCircle } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from 'react-router-dom';
import axios from '../api/axios';

import '../styles/login-register.css';

const USER_REGEX = /^[A-z][A-z0-9-_]{3,20}$/;
const STUDENT_NAME_REGEX = /^[A-Z][a-z]+ (?:[A-Z][a-z]+)* [A-Z][a-z]+$/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,20}$/;
const EMAIL_REGEX = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
const REGISTER_URL = '/students/addstudent';

const Register = () => {
    const userRef = useRef();
    const studentNameRef = useRef();
    const errRef = useRef();

    const [user, setUser] = useState('');
    const [validName, setValidName] = useState(false);
    const [userFocus, setUserFocus] = useState(false);

    const [studentName, setStudentName] = useState("");
    const [validStudentName, setValidStudentName] = useState(false);
    const [studentNameFocus, setStudentNameFocus] = useState(false);

    const [email, setEmail] = useState('');
    const [validEmail, setValidEmail] = useState(false);
    const [emailFocus, setEmailFocus] = useState(false);

    const [pwd, setPwd] = useState('');
    const [validPwd, setValidPwd] = useState(false);
    const [pwdFocus, setPwdFocus] = useState(false);

    const [matchPwd, setMatchPwd] = useState('');
    const [validMatch, setValidMatch] = useState(false);
    const [matchFocus, setMatchFocus] = useState(false);

    const [address, setAddress] = useState('');
    const [validAddress, setValidAddress] = useState(false);
    const [addressFocus, setAddressFocus] = useState(false);

    const [errMsg, setErrMsg] = useState('');
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        userRef.current.focus();
    }, [])

    useEffect(() => {
        setValidName(USER_REGEX.test(user));
    }, [user])

    useEffect(() => {
        setValidStudentName(STUDENT_NAME_REGEX.test(studentName));
    }, [studentName])

    useEffect(() => {
        setValidEmail(EMAIL_REGEX.test(email));
    }, [email])

    useEffect(() => {
        setValidPwd(PWD_REGEX.test(pwd));
        setValidMatch(pwd === matchPwd);
    }, [pwd, matchPwd])

    useEffect(() => {
        setValidAddress(address.length > 0);
    }, [address])

    useEffect(() => {
        setErrMsg('');
    }, [user, email, pwd, matchPwd, address])

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Validate inputs
        const v1 = USER_REGEX.test(user);
        const v2 = STUDENT_NAME_REGEX.test(studentName);
        const v3 = PWD_REGEX.test(pwd);
        const v4 = EMAIL_REGEX.test(email);
        const v5 = address.length > 0;

        if (!v1 || !v2 || !v3 || !v4 || !v5 || !validMatch) {
            setErrMsg("Invalid Entry");
            return;
        }

        try {
            const response = await axios.post(REGISTER_URL, {
                student_name: studentName,
                username: user,
                email: email,
                password: pwd,
                address: address
            });
            console.log("Response:", response);

            // Handle success
            setSuccess(true);
            setUser('');
            setStudentName('');
            setEmail('');
            setPwd('');
            setMatchPwd('');
            setAddress('');
        } catch (err) {
            console.error('Registration failed:', err.response ? err.response.data : err.message);
            setErrMsg('Registration Failed');
            errRef.current.focus();
        }
    };

    return (
        <div className="app-container">
            {success ? (
                <section className="form-container">
                    <h1>Success!</h1>
                    <p>
                        <Link to="/">Sign In</Link>
                    </p>
                </section>
            ) : (
                <section className="form-container">
                    <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">{errMsg}</p>
                    <h1>Register</h1>
                    <form onSubmit={handleSubmit}>
                        <label htmlFor="username">
                            Username:
                            <FontAwesomeIcon icon={faCheck} className={validName ? "valid" : "hide"} />
                            <FontAwesomeIcon icon={faTimes} className={validName || !user ? "hide" : "invalid"} />
                        </label>
                        <input
                            type="text"
                            id="username"
                            ref={userRef}
                            autoComplete="off"
                            onChange={(e) => setUser(e.target.value)}
                            value={user}
                            required
                            // placeholder='Example : YourName@24'
                            aria-invalid={validName ? "false" : "true"}
                            aria-describedby="uidnote"
                            onFocus={() => setUserFocus(true)}
                            onBlur={() => setUserFocus(false)}
                        />
                        <p id="uidnote" className={userFocus && user && !validName ? "instructions" : "offscreen"}>
                            <FontAwesomeIcon icon={faInfoCircle} />
                            4 to 20 characters.<br />
                            Must begin with a letter.<br />
                            Letters, numbers, underscores, hyphens allowed.
                        </p>
                        <label htmlFor="studentName">
                            Student Name:
                            <FontAwesomeIcon icon={faCheck} className={validStudentName ? "valid" : "hide"} />
                            <FontAwesomeIcon icon={faTimes} className={validStudentName || !studentName ? "hide" : "invalid"} />
                        </label>
                        <input
                            type="text"
                            id="studentName"
                            ref={studentNameRef}
                            autoComplete="off"
                            onChange={(e) => setStudentName(e.target.value)}
                            value={studentName}
                            required
                            aria-invalid={validStudentName ? "false" : "true"}
                            aria-describedby="uidnote2"
                            onFocus={() => setStudentNameFocus(true)}
                            onBlur={() => setStudentNameFocus(false)}
                        />
                        <p id="uidnote2" className={studentNameFocus && studentName && !validStudentName ? "instructions" : "offscreen"}>
                            <FontAwesomeIcon icon={faInfoCircle} />
                            Name should be in the format FirstName MiddleName LastName.
                        </p>
                        <label htmlFor="email">
                            Email:
                            <FontAwesomeIcon icon={faCheck} className={validEmail ? "valid" : "hide"} />
                            <FontAwesomeIcon icon={faTimes} className={validEmail || !email ? "hide" : "invalid"} />
                        </label>
                        <input
                            type="email"
                            id="email"
                            onChange={(e) => setEmail(e.target.value)}
                            value={email}
                            required
                            aria-invalid={validEmail ? "false" : "true"}
                            aria-describedby="emailnote"
                            onFocus={() => setEmailFocus(true)}
                            onBlur={() => setEmailFocus(false)}
                        />
                        <p id="emailnote" className={emailFocus && email && !validEmail ? "instructions" : "offscreen"}>
                            <FontAwesomeIcon icon={faInfoCircle} />
                            Must be a valid email address.
                        </p>
                        <label htmlFor="password">
                            Password:
                            <FontAwesomeIcon icon={faCheck} className={validPwd ? "valid" : "hide"} />
                            <FontAwesomeIcon icon={faTimes} className={validPwd || !pwd ? "hide" : "invalid"} />
                        </label>
                        <input
                            type="password"
                            id="password"
                            onChange={(e) => setPwd(e.target.value)}
                            value={pwd}
                            required
                            aria-invalid={validPwd ? "false" : "true"}
                            aria-describedby="pwdnote"
                            onFocus={() => setPwdFocus(true)}
                            onBlur={() => setPwdFocus(false)}
                        />
                        <p id="pwdnote" className={pwdFocus && !validPwd ? "instructions" : "offscreen"}>
                            <FontAwesomeIcon icon={faInfoCircle} />
                            8 to 20 characters.<br />
                            Must include uppercase, lowercase, number, and special character.<br />
                            Allowed special characters: ! @ # $ %
                        </p>
                        <label htmlFor="confirm_pwd">
                            Confirm Password:
                            <FontAwesomeIcon icon={faCheck} className={validMatch && matchPwd ? "valid" : "hide"} />
                            <FontAwesomeIcon icon={faTimes} className={validMatch || !matchPwd ? "hide" : "invalid"} />
                        </label>
                        <input
                            type="password"
                            id="confirm_pwd"
                            onChange={(e) => setMatchPwd(e.target.value)}
                            value={matchPwd}
                            required
                            aria-invalid={validMatch ? "false" : "true"}
                            aria-describedby="confirmnote"
                            onFocus={() => setMatchFocus(true)}
                            onBlur={() => setMatchFocus(false)}
                        />
                        <p id="confirmnote" className={matchFocus && !validMatch ? "instructions" : "offscreen"}>
                            <FontAwesomeIcon icon={faInfoCircle} />
                            Must match the first password input field.
                        </p>
                        <label htmlFor="address">
                            Address:
                            <FontAwesomeIcon icon={faCheck} className={validAddress ? "valid" : "hide"} />
                            <FontAwesomeIcon icon={faTimes} className={validAddress || !address ? "hide" : "invalid"} />
                        </label>
                        <textarea
                            id="address"
                            onChange={(e) => setAddress(e.target.value)}
                            value={address}
                            required
                            aria-invalid={validAddress ? "false" : "true"}
                            aria-describedby="addressnote"
                            onFocus={() => setAddressFocus(true)}
                            onBlur={() => setAddressFocus(false)}
                        />
                        <p id="addressnote" className={addressFocus && !validAddress ? "instructions" : "offscreen"}>
                            <FontAwesomeIcon icon={faInfoCircle} />
                            Address cannot be empty.
                        </p>
                        <button disabled={!validName || !validStudentName || !validEmail || !validPwd || !validMatch || !validAddress}>
                            Register
                        </button>
                    </form>
                    <p>
                        Already have an account?<br />
                        <span className="line">
                            <Link to="/">Sign In</Link>
                        </span>
                    </p>
                </section>
            )}
        </div>
    );
};

export default Register;
