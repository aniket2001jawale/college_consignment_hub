import React, { useState,useEffect, useContext } from 'react';
import { BrowserRouter as Router, Routes, Route, useLocation } from 'react-router-dom';
import Navbar from './components/Navbar';
import Inventory from './components/Inventory,';
import Cart from './components/Cart';
import Product from './components/Product';
import ProductDetail from './components/ProductDetail';
import SearchItem from './components/SearchItem';
import Login from './components/Login';
import Register from './components/Register';
import AddItem from "./components/AddItem";
import AppContext from './context/AppContext'; // Make sure to import AppContext
import AppProvider from './context/AppState'; // Make sure to import AppContext
import UserProfile from './components/UserProfile';
import { ToastContainer } from 'react-toastify';
import EditProduct from './components/EditItem';
//import DonateItem from './components/DonateItemForm';


const App = () => {
  const { products } = useContext(AppContext); // Correctly use useContext

  useEffect(() => {
    setData(products);
  }, [products]);
  const [cart, setCart] = useState([]);
  const [data, setData] = useState(products); // Use products from context
  console.log(products);
  const location = useLocation();
  const showNavbar = location.pathname !== '/' && location.pathname !== '/register' && location.pathname !== '/profile' ;
 

  return (
    <>
      <ToastContainer />
      {showNavbar  && <Navbar setData={setData} cart={cart} />}
      <Routes>
        <Route path="/home" element={<Product items={data} cart={cart} setCart={setCart} />} />
        <Route path="/product/:id" element={<ProductDetail cart={cart} setCart={setCart} />} />
        <Route path="/search/:term" element={<SearchItem cart={cart} setCart={setCart} />} />
        <Route path="/cart" element={<Cart cart={cart} setCart={setCart} />} />
        <Route path="/sellitem" element={<AddItem />} />
        <Route path="/edit-item/:id" element={<EditProduct />} />
        <Route path="/" element={<Login />} />
        <Route path="/inventory" element={<Inventory />} />
    {/* <Route path="/donate" element={DonateItem}/> */}
        <Route path="/register" element={<Register />} />
        <Route path="/profile" element={<UserProfile />} /> 
      </Routes>
    </>
  );
};

const AppWrapper = () => (
  <Router>
    <AppProvider>
      <App />
    </AppProvider>
  </Router>
);

export default AppWrapper;
