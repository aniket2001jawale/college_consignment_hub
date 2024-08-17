import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/Product.css';

const Product = ({ items, cart, setCart }) => {
  const addToCart = (product) => {
    if (cart.find((item) => item.item_id === product.item_id)) {
      alert('Item already in cart');
    } else {
      setCart([...cart, product]);
    }
  };

  return (
    <div className="product-container">
      {items.length === 0 ? (
        <p>No products available</p>
      ) : (
        items.map((product) => (
          <div className="product-card" key={product.item_id}>
            <img src={product.image} alt={product.title} />
            <div className="product-details">
              <h5>{product.title}</h5>
              {/* <p>Duration: {product.item_duration}</p> */}
              {/* <p>Status: {product.status}</p> */}
              <p>Price: {product.price} ₹</p>
              {/* <p>Cart ID: {product.cart_id}</p> */}
              {/* <p>Category ID: {product.category_id}</p>
              <p>Seller ID: {product.seller_id}</p> */}
              {/* <p>Watchlist ID: {product.watchlist_id}</p> */}
              {/* <p>Description: {product.description}</p> */}
              <div className="button-group">
                <Link to={`/product/${product.item_id}`} className="btn btn-view">View</Link>
                <button onClick={() => addToCart(product)} className="btn btn-add">Add to Wishlist</button>
              </div>
            </div>
          </div>
        ))
      )}
    </div>
  );
};

export default Product;
