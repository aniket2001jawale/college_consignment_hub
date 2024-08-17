import React, { useState, useEffect, useContext } from 'react';
import axios from '../api/axios';
import AppContext from '../context/AppContext';
import '../styles/Inventory.css';
import {Link, useNavigate } from 'react-router-dom';


const storedStudent = JSON.parse(localStorage.getItem('student'));

const Inventory = () => {
  const { student } = useContext(AppContext);
  const [items, setItems] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    if (!student) return;

    const fetchItems = async () => {
      try {
        const response = await axios.get('/item');
        const userItems = response.data.filter(item => item.seller_id === student.student_id);
        setItems(userItems);
      } catch (error) {
        console.error('Failed to fetch items', error);
      }
    };

    fetchItems();
  }, [student]);

  const handleEdit = (id) => {
    navigate(`/edit-item/${id}`);
  };

  const handleDelete = async (id) => {
    try {
      console.log("Id is this : "+id);
      await axios.delete(`/item/${id}`);
      setItems(items.filter(item => item.item_id !== id));
    } catch (error) {
      console.error('Failed to delete item', error);
    }
  };

  const handleAddItem = () => {
    navigate('/add-item'); // Navigate to the Add Item page
  };

  return (
    <div className="inventory">
      <h2>{storedStudent.username} Cart</h2>
      <Link to="/sellitem">
      <button className="add-item-button" onClick={handleAddItem}>Sell Item</button>
      </Link>
      <table className="inventory-table">
        <thead>
          <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Price</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {items.length > 0 ? (
            items.map(item => (
              <tr key={item.item_id}>
                <td>{item.title}</td>
                <td>{item.description}</td>
                <td>₹{item.price}</td>
                <td>
                  <button onClick={() => handleEdit(item.item_id)}>Edit</button>
                  <button onClick={() => handleDelete(item.item_id)}>Delete</button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="4">No items found</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default Inventory;
