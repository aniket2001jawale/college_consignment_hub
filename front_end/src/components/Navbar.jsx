import React, { useState, useContext, useEffect } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import AppContext from "../context/AppContext";
import axios from "axios";
import { BsCardList, BsFillCartCheckFill, BsFillSignTurnRightFill, BsHeartFill } from "react-icons/bs";
import "../styles/Navbar.css";

const Navbar = ({ setData, cart }) => {
  const location = useLocation();
  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState("");
  const { products } = useContext(AppContext); 
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await axios.get("http://localhost:8080/category");
        setCategories(response.data);
      } catch (error) {
        console.error("Error fetching categories:", error);
      }
    };
    fetchCategories();
  }, []);

  const filterByCategory = (categoryId) => {
    const filteredItems = products.filter(
      (product) => product.category_id === categoryId
    );
    setData(filteredItems);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    navigate(`/search/${searchTerm}`);
    setSearchTerm("");
  };

  const handleLogout = () => {
    // Display a confirmation dialog
    const isConfirmed = window.confirm("Are you sure you want to log out?");
    
    // If the user confirms, proceed with the logout
    if (isConfirmed) {
      localStorage.removeItem("student");
      navigate("/"); 
    }
  };
  

  return (
    <nav className="navbar">
      <div className="navbar-top">
        <Link to="/home" className="brand">
          <img
            src="https://media-exp1.licdn.com/dms/image/C4E0BAQH0pV14FxzqwQ/company-logo_200_200/0/1624685506949?e=2159024400&v=beta&t=TqDgxbAurWqG62Jn5PhQil_A3YxO84IwrqlvNqB_vAc"
            alt="CCHub Logo"
            className="brand-logo"
          />
        </Link>

        <form className="search-bar" onSubmit={handleSubmit}>
          <input
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            type="text"
            placeholder="Search Products"
          />
        </form>

        <Link to="/cart" className="cart">
          <button className="btn-cart">
         <BsHeartFill/>
            <span className="badge">{cart.length}</span>
          </button>
        </Link>
        <Link to="/inventory">
          <button className="btn-user-profile">   <BsFillCartCheckFill /></button>
        </Link>
        {/* <Link to="/sellitem">
          <button className="btn-sell-item">Sell Item</button>
        </Link> */}
        <Link to="/profile">
          <button className="btn-user-profile">User Profile</button>
        </Link>
       
        <button className="btn-logout" onClick={handleLogout}>
          Logout
        </button>
      </div>

      {location.pathname === "/home" && (
        <div className="filter-menu">
          <button onClick={() => setData(products)}>No Filter</button>
          {categories.map((category) => (
            <button
              key={category.category_id}
              onClick={() => filterByCategory(category.category_id)}
            >
              {category.category_name}
            </button>
          ))}
        </div>
      )}
    </nav>
  );
};

export default Navbar;
