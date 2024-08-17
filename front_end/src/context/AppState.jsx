import React, { useState, useEffect } from 'react';
import AppContext from './AppContext';
import axios from '../api/axios';

const AppProvider = (props) => {
  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState([]);
  const [filteredData, setFilteredData] = useState([]);
  const [student, setStudent] = useState(null); // Default to null

  useEffect(() => {
    const fetchStudentData = () => {
      const studentFromLocalStorage = JSON.parse(localStorage.getItem('student'));
      if (studentFromLocalStorage) {
        setStudent(studentFromLocalStorage);
      }
    };
    fetchStudentData();
  }, []);

  useEffect(() => {
    if (!student) return; // Exit early if student is not available

    const fetchAllProducts = async () => {
      try {
        const response = await axios.get('/item', {
          headers: { 'Content-Type': 'application/json' },
          withCredentials: true,
        });
        setProducts(response.data);
        setFilteredData(response.data);
      } catch (error) {
        console.error('Failed to fetch products', error);
      }
    };

    const fetchCart = async () => {
      try {
        const cartIdResponse = await axios.get(`/cart/student/${student.student_id}`, {
          headers: { 'Content-Type': 'application/json' },
          withCredentials: true,
        });
        const cartId = cartIdResponse.data.cart_id;

        const response = await axios.get(`/item/cart/${cartId}`, {
          headers: { 'Content-Type': 'application/json' },
          withCredentials: true,
        });
        setCart(response.data);
        localStorage.setItem('cart', JSON.stringify(response.data));
      } catch (error) {
        console.error('Failed to fetch cart', error);
      }
    };

    fetchAllProducts();
    fetchCart();
  }, [student]);

  const logout = () => {
    localStorage.removeItem('student');
    setStudent(null);
  };

  const addToCart = async (product) => {
    if (!student) return; // Exit early if student is not available

    try {
      const cartIdResponse = await axios.get(`/cart/student/${student.student_id}`, {
        headers: { 'Content-Type': 'application/json' },
        withCredentials: true,
      });
      const cartId = cartIdResponse.data.cart_id;

      await axios.post('/item', {
        cart_id: cartId,
        item_id: product.item_id,
      }, {
        headers: { 'Content-Type': 'application/json' },
        withCredentials: true,
      });

      const response = await axios.get(`/item/cart/${cartId}`, {
        headers: { 'Content-Type': 'application/json' },
        withCredentials: true,
      });
      setCart(response.data);
    } catch (error) {
      console.error('Failed to add to cart', error);
    }
  };

  const removeFromCart = async (id) => {
    if (!student) return; // Exit early if student is not available

    try {
      await axios.delete(`/cart/${id}`, {
        headers: { 'Content-Type': 'application/json' },
        withCredentials: true,
      });

      const cartIdResponse = await axios.get(`/cart/student/${student.student_id}`, {
        headers: { 'Content-Type': 'application/json' },
        withCredentials: true,
      });
      const cartId = cartIdResponse.data.cart_id;

      const response = await axios.get(`/item/cart/${cartId}`, {
        headers: { 'Content-Type': 'application/json' },
        withCredentials: true,
      });
      setCart(response.data);
    } catch (error) {
      console.error('Failed to remove from cart', error);
    }
  };

  return (
    <AppContext.Provider value={{
      products,
      cart,
      addToCart,
      removeFromCart,
      filteredData,
      setFilteredData,
      student,
      logout
    }}>
      {props.children}
    </AppContext.Provider>
  );
};

export default AppProvider;
