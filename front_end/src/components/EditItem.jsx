import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "../api/axios";


const UPDATE_PRODUCT_URL = '/item'; // Endpoint to update a product
const student = JSON.parse(localStorage.getItem("student"));

const EditProduct = () => {

  const { id } = useParams(); // Get the product ID from the URL
  const navigate = useNavigate();
  const [productData, setProductData] = useState({
    title: "",
    description: "",
    price: "",
    image: "",
    item_duration: "",
    status: "AVAILABLE",
    seller_id: student.student_id,
    cart_id: "",
    category_id: "",
    watchlist_id: ""
  });

  const [categories, setCategories] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        // Fetch product details
        const productResponse = await axios.get(`/item/${id}`);
        setProductData(productResponse.data);

        // Fetch categories
        const categoriesResponse = await axios.get('/category');
        setCategories(categoriesResponse.data);

      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, [id]);

  const onChangeHandler = (e) => {
    const { name, value } = e.target;
    setProductData({ ...productData, [name]: value });
  };

  const updateProduct = async (product) => {
    try {
      const response = await axios.put(`${UPDATE_PRODUCT_URL}/${id}`, product);
      return response.data;
    } catch (error) {
      console.error("Error updating product:", error);
      throw error;
    }
  };

  const onSubmitHandler = async (e) => {
    e.preventDefault();
    if (productData.category_id === "" || productData.category_id === "--Select Category--") {
      alert("Please select a category");
    } else {
      try {
        await updateProduct(productData);
        alert("Item Updated Successfully!!");
        setTimeout(() => {
          navigate('/inventory'); // Redirect to inventory page after update
        }, 2000);
      } catch (error) {
        alert("Failed to update product");
      }
    }
  };

  return (
    <div className="container my-5 p-4" style={{ width: "580px", border: "1px solid yellow", borderRadius: "10px" }}>
      <h1 className="text-center">Edit Product</h1>
      <form onSubmit={onSubmitHandler}>
        <div className="mb-3">
          <label htmlFor="category_id" className="form-label">Category</label>
          <select
            className="form-select"
            name="category_id"
            value={productData.category_id}
            onChange={onChangeHandler}
            id="category_id"
            required
          >
            <option value="">--Select Category--</option>
            {categories.map((category) => (
              <option key={category.category_id} value={category.category_id}>
                {category.category_name}
              </option>
            ))}
          </select>
        </div>

        <div className="mb-3">
          <label htmlFor="title" className="form-label">Title</label>
          <input
            name="title"
            value={productData.title}
            onChange={onChangeHandler}
            type="text"
            className="form-control"
            id="title"
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="description" className="form-label">Description</label>
          <input
            name="description"
            value={productData.description}
            onChange={onChangeHandler}
            type="text"
            className="form-control"
            id="description"
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="price" className="form-label">Price</label>
          <input
            name="price"
            value={productData.price}
            onChange={onChangeHandler}
            type="number"
            className="form-control"
            id="price"
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="image" className="form-label">Image</label>
          <input
            name="image"
            value={productData.image}
            onChange={onChangeHandler}
            type="text"
            className="form-control"
            id="image"
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="item_duration" className="form-label">Item Duration</label>
          <input
            name="item_duration"
            value={productData.item_duration}
            onChange={onChangeHandler}
            type="text"
            className="form-control"
            id="item_duration"
            required
          />
        </div>

        <div className="d-grid col-6 my-5 mx-auto">
          <button type="submit" className="btn btn-primary">Update Product</button>
        </div>
      </form>
    </div>
  );
};

export default EditProduct;
