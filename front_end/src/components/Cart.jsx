import React from 'react';
import { Link ,useNavigate} from 'react-router-dom';
import '../styles/Cart.css';



const Cart = ({ cart, setCart }) => {
  const navigate=useNavigate();
  const handleRemove = (id) => {
    setCart(cart.filter(product => product.item_id !== id));
  };

  const handleClearCart = () => {
    setCart([]);
  };

  const handleHome = () => {
    navigate('/home');
  };

  return (
    <div className="cart">
      {cart.length === 0 ? (
        <div className="empty-cart">
          <h1>Your Cart is Empty</h1>
          <Link to="/home" className="btn btn-continue">Continue Shopping...</Link>
        </div>
      ) : (
        <>
          {cart.map((product) => (
            <div className="cart-item" key={product.item_id}>
              <img src={product.image} alt={product.title} />
              <div className="cart-details">
                <h5>{product.title}</h5>
                <p>{product.description}</p>
                <div className="button-group">
                  <button onClick={() => handleRemove(product.item_id)} className="btn btn-remove">Remove</button>
                  <button className="btn btn-buy">Contact Now</button>
                </div>
              </div>
            </div>
          ))}
          <div className="checkout">
            <button className="btn btn-checkout" onClick={handleHome}>Back To Homepage</button>
            <button onClick={handleClearCart} className="btn btn-clear">Clear Cart</button>
          </div>
        </>
      )}
    </div>
  );
};

export default Cart;
