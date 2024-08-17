import React, { useEffect, useState, useContext } from 'react';
import { useParams } from 'react-router-dom';
import AppContext from '../context/AppContext'; // Import AppContext
import Product from './Product';
import '../styles/SearchItem.css';

const SearchItem = ({ cart, setCart }) => {
  const { term } = useParams();
  const [filterData, setFilterData] = useState([]);
  const { products } = useContext(AppContext); // Get products from context

  useEffect(() => {
    const filteredData = () => {
      // Filter products based on the search term
      const data = products.filter((p) =>
        p.title.toLowerCase().includes(term.toLowerCase())
      );
      setFilterData(data);
    };

    filteredData();
  }, [term, products]); // Add products to dependency array

  return (
    <div className="search-results">
      <Product items={filterData} cart={cart} setCart={setCart} />
    </div>
  );
};

export default SearchItem;
