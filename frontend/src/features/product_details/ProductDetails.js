import React, { useState, useEffect } from 'react';
import placeholder from '../../assets/images/placeholder.png';
import { useParams } from 'react-router-dom';
import SubHeader from '../../components/SubHeader';
import HeartIcon from '../../assets/icons/heart-regular.svg';
import Header from "../../components/Header";
import FilledHeartIcon from '../../assets/icons/heart-solid.svg';
import StarIcon from '../../assets/icons/star-regular.svg';
import FilledStarIcon from '../../assets/icons/star-solid.svg';
import ErrorAlert from '../../components/ErrorAlert';
import ProductDetailsApi from '../../services/ProductDetailsApi'; // Import the new service
import { TradeRequestApi } from '../../services/TradeRequestApi';
import {useUser} from '../authentication/UserContext'

const ProductDetails = () => {
  const { productId } = useParams();

  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [errors, setError] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [requestedAmount, setRequestedAmount] = useState('');
  const [amountError, setAmountError] = useState('');
  const headerConfig = {
    search: false,
    requests: true,
    notifications: true,
    add: true,
    profile: true
  };
  const { user } =  1 //useUser();  // Get the user function from the context
  console.log("user", user)
  console.log("product", product)



  function capitalizeFirstLetter(str) {
    if (!str) return "";
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
  }

  useEffect(() => {
    fetchProductData(productId);
  }, [productId]);

  const fetchProductData = async (productId) => {
    try {
      const data = await ProductDetailsApi.fetchProductDetails(productId);
      setProduct(data);
      setLoading(false);
    } catch (error) {
      console.error('Failed to fetch product data', error);
      setError(error);
      setLoading(false);
    }
  };

  const addtoFavorite = async (productId) => {
    try {
      await ProductDetailsApi.addToFavorite(productId);
      setProduct({ ...product, favorite: !product.favorite });
    } catch (error) {
      console.error('Failed to add to favorites', error);
    }
  };

  const createTradeRequest = async () => {
    if (!requestedAmount.trim()) {
      setAmountError('Please enter a valid amount');
      return;
    }

    if (isNaN(requestedAmount) || Number(requestedAmount) <= 0) {
      setAmountError('Please enter a valid positive number');
      return;
    }

    const requestBody = {
      productId: product.productId,
      sellerId: product.sellerId, // TODO: Add actual seller ID
      requestedPrice: requestedAmount // Replace with input value
    };
    try {
      await TradeRequestApi.create(requestBody);
      setShowModal(false); 
      setRequestedAmount('');
      setAmountError('');
    } catch (error) {
      console.error('Failed to create trade request', error);
    }
  };

  const StarRating = ({ starCount }) => {
    const totalStars = 5;
    const stars = [];

    for (let i = 0; i < totalStars; i++) {
      if (i < starCount) {
        stars.push(
          <img key={i} src={FilledStarIcon} alt="star" className="h-6 w-6" />
        );
      } else {
        stars.push(
          <img key={i} src={StarIcon} alt="star" className="h-6 w-6" />
        );
      }
    }

    return <div className="flex flex-row">{stars}</div>;
  };

  const toggleModal = () => {
    setShowModal(!showModal);
    setRequestedAmount('');
    setAmountError('');
  };

  if (loading) {
    return <p>Loading...</p>;
  }

  return (
    <div>
      <Header config={headerConfig} />
      <SubHeader title={'Products'} backPath={'/products'} />
      {errors && <ErrorAlert message={errors.message} />}
      {!errors && <main className="flex-1 p-6">
        <div className="grid grid-cols-1 md:grid-cols-10 gap-8">
          <div className="md:col-span-3">
            <img
              alt="Product"
              className="w-full rounded-lg"
              height={600}
              src={product?.imageUrl || placeholder}
              style={{
                aspectRatio: '1/1',
                objectFit: 'cover',
              }}
              width={600}
            />
            <div className="grid grid-cols-4 gap-4 mt-4">
              {[1, 2, 3, 4].map((index) => (
                <button key={index} className="border rounded-lg overflow-hidden">
                  <img
                    alt={`Thumbnail ${index}`}
                    className="w-full aspect-square object-cover"
                    height={100}
                    src={placeholder}
                    width={100}
                  />
                </button>
              ))}
            </div>
          </div>
          <div className="md:col-span-7">
            <h1 className="text-3xl font-bold mb-5 mt-3">{product?.title}</h1>
            <p className="text-gray-600 mb-6">{product?.description}</p>

            <div className="flex items-center justify-between mb-6">
              <h2 className="text-2xl font-bold">${product?.price}</h2>
              {console.log(product?.sellerId !== user?.userId,"product & seller id")}
             { (product?.sellerId !== user?.userId)  ? <div className="flex items-center gap-4 mr-12">
                <button
                  className="flex flex-end gap-2 bg-transparent text-700 font-semibold py-2 px-4 border-2 border-gray-300 rounded"
                  onClick={() => addtoFavorite(product?.productId)}
                >
                  {product?.favorite ?
                    (<img src={FilledHeartIcon} alt="heart" className="h-6 w-6" />) :
                    <img src={HeartIcon} alt="heart" className="h-6 w-6" />}
                  Favorite
                </button>
                <button
                  onClick={toggleModal}
                  className="bg-transparent text-700 font-semibold py-2 px-4 border-2 border-gray-300 rounded hover:bg-gray-100"
                >
                  Send Buy Request
                </button>
              </div> :""}
            </div>
            <div className="grid grid-cols-5 gap-7">
              <div>
                <h3 className="text-base font-medium mb-2">Condition</h3>
                <p className="text-gray-600">{product?.productCondition}</p>
              </div>
              <div>
                <h3 className="text-base font-medium mb-2">Status</h3>
                <p className="text-gray-600">
                  {product?.quantityAvailable > 0
                    ? 'Available'
                    : 'Unavailable'}
                </p>
              </div>
              <div>
                <h3 className="text-base font-medium mb-2">Shipping</h3>
                <p className="text-gray-600">
                  {capitalizeFirstLetter(`${product?.shippingType}`)} Shipping
                </p>
              </div>
              <div>
                <h3 className="text-base font-medium mb-2">Category</h3>
                <p className="text-gray-600">{product?.category}</p>
              </div>
              <div>
                <h3 className="text-base font-medium mb-2">Use Duration</h3>
                <p className="text-gray-600">{product?.useDuration}</p>
              </div>
            </div>
            <div className="grid grid-cols-2 gap-4 mt-10">
              <div>
                <h3 className="text-base font-bold font-medium mb-2">Seller</h3>
                <div className="flex items-center gap-2">
                  <div>
                    <p className="font-medium">{product?.sellerName}</p>
                    <p className="text-gray-600 text-sm mt-2">
                      Seller since {product?.sellerJoiningDate || '2020'}
                    </p>
                    <div className="flex flew-col items-center gap-1 text-xs font-semibold">
                      <StarRating starCount={3} />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>}
      {showModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-gray-800 bg-opacity-75">
          <div className="relative p-4 w-full max-w-md">
            <div className="relative bg-white rounded-lg shadow">
              <button
                type="button"
                className="absolute top-3 right-3 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 flex justify-center items-center"
                onClick={toggleModal}
              >
                <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                  <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 6 6m0 0 6-6m-6 6 6 6m-6 6-6-6"></path>
                </svg>
              </button>
              <div className="p-4">
                <h2 className="text-xl font-semibold mb-4">Send Buy Request</h2>
                <div className="mb-4">
                  <label htmlFor="requestedAmount" className="block text-sm font-medium text-gray-700 mb-1">
                    Enter Amount:
                  </label>
                  <input
                    type="text"
                    id="requestedAmount"
                    name="requestedAmount"
                    value={requestedAmount}
                    onChange={(e) => setRequestedAmount(e.target.value)}
                    placeholder="Enter price"
                    className={`border border-gray-300 rounded-lg py-2 px-3 w-full focus:outline-none focus:border-blue-500 ${amountError ? 'border-red-500' : ''}`}
                  />
                  {amountError && (
                    <p className="text-red-500 text-xs mt-1">{amountError}</p>
                  )}
                </div>
                <div className="flex justify-end">
                  <button
                    className="bg-blue-500 text-white py-2 px-4 rounded-lg font-semibold hover:bg-blue-600 mr-2"
                    onClick={createTradeRequest}
                  >
                    Send Request
                  </button>
                  <button
                    className="bg-red-500 text-white py-2 px-4 rounded-lg font-semibold hover:bg-red-600"
                    onClick={toggleModal}
                  >
                    Cancel
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default ProductDetails;

