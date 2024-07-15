import React, { useState, useEffect } from "react";
import Header from "../../components/Header";
import axios from "axios";
import placeholder from "../../assets/images/placeholder.png";
import { useParams } from "react-router-dom";
import SubHeader from "../../components/SubHeader";

import HeartIcon from "../../assets/icons/heart-regular.svg";
import FilledHeartIcon from "../../assets/icons/heart-solid.svg";
import StarIcon from "../../assets/icons/star-regular.svg";
import FilledStarIcon from "../../assets/icons/star-solid.svg";
import { TradeRequestApi } from "../../services/TradeRequestApi";

const ProductDetails = () => {
  const { productId } = useParams();

  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  function capitalizeFirstLetter(str) {
    if (!str) return "";
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
  }

  async function addtoFavorite(productId) {
    try {
      const response = await axios.get(
        `http://localhost:8080/product_details/favorite`, {
          params: {
            productId: productId
          },
          paramsSerializer: {indexes: null }
        }
      );
      console.log(response, "response");
      if (response.status === 200) {
        setProduct({...product, favorite: !product.favorite});
      }
    } catch (error) {}
  }

  useEffect(() => {
    const fetchProductData = async () => {
      try {
        console.log({ productId });
        const response = await axios.get('http://localhost:8080/product_details', { 
          params: {
            productId: productId
          },
          paramsSerializer: {indexes: null }
        });
        const data = response.data;
        console.log(data, "product data");
        setProduct(data);
        setLoading(false);
      } catch (error) {
        console.error("Failed to fetch product data", error);
        setError(error);
        setLoading(false);
      }
    };
    fetchProductData();
  }, [productId]);

  const createTradeRequest = async () => {
    const requestBody = {
      productId: product.productId,
      sellerId: 1, // TODO: Add add product seller user ID
      requestedPrice: product.price // TODO: replace with actual requested price
    };
    await TradeRequestApi.create(requestBody);
  }

  const StarRating = ({ starCount }) => {
    const totalStars = 5;
    const stars = [];
  
    for (let i = 0; i < totalStars; i++) {
      if (i < starCount) {
        stars.push(
          <img key={i} src={FilledStarIcon} alt="star" className="h-6 w-6"></img>
        );
      } else {
        stars.push(
          <img key={i} src={StarIcon} alt="star" className="h-6 w-6"></img>
        );
      }
    }
  
    return <div className="flex flex-row">{stars}</div>;
  };

  return (
    !loading && <div>
      <Header />
      <SubHeader title={'Product Details'} backPath={'/products'} />
      <div className="flex flex-1">
        {error && <div className="flex justify-center h-16 w-full" >
            <div className="flex items-center py-4 px-12 mt-4 text-sm text-red-600 rounded-lg bg-red-50 border-2 border-red-600" role="alert">
              <span className="sr-only">Error</span>
              <div>
                <span className="font-medium">Error!</span> {error.message}
              </div>
            </div>
          </div>}
        {!error && <main className="flex-1 p-6">
          <div className="grid grid-cols-1 md:grid-cols-10 gap-8">
            <div className="md:col-span-3">
              <img
                alt="Product"
                className="w-full rounded-lg"
                height={600}
                src={placeholder}
                style={{
                  aspectRatio: "1/1",
                  objectFit: "cover",
                }}
                width={600}
              />
              <div className="grid grid-cols-4 gap-4 mt-4">
                <button className="border rounded-lg overflow-hidden">
                  <img
                    alt=""
                    className="w-full aspect-square object-cover"
                    height={100}
                    src={placeholder}
                    width={100}
                  />
                </button>
                <button className="border rounded-lg overflow-hidden">
                  <img
                    alt="Thumbnail 2"
                    className="w-full aspect-square object-cover"
                    height={100}
                    src={placeholder}
                    width={100}
                  />
                </button>
                <button className="border rounded-lg overflow-hidden">
                  <img
                    alt="Thumbnail 3"
                    className="w-full aspect-square object-cover"
                    height={100}
                    src={placeholder}
                    width={100}
                  />
                </button>
                <button className="border rounded-lg overflow-hidden">
                  <img
                    alt="Thumbnail 4"
                    className="w-full aspect-square object-cover"
                    height={100}
                    src={placeholder}
                    width={100}
                  />
                </button>
              </div>
            </div>
            <div className="md:col-span-7">
              <h1 className="text-3xl font-bold mb-5 mt-3">{product?.title}</h1>
              <p className="text-gray-600 mb-6">{product?.description}</p>

              <div className="flex items-center justify-between mb-6">
                <h2 className="text-2xl font-bold">${product?.price}</h2>
                <div className="flex items-center gap-4 mr-12">
                  <button
                    className="flex flex-end gap-2 bg-transparent text-700 font-semibold py-2 px-4 border-2 border-gray-300 rounded"
                    onClick={() => addtoFavorite(product?.productId)}>
                    {product.favorite ? 
                      (<img src={FilledHeartIcon} alt="heart" className="h-6 w-6"></img>) : 
                      <img src={HeartIcon} alt="heart" className="h-6 w-6"></img>} Favorite
                  </button>
                  <button
                    onClick={() => createTradeRequest()}
                    className="bg-transparent text-700 font-semibold py-2 px-4 border-2 border-gray-300 rounded">
                    Send Buy Request
                  </button>
                </div>
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
                      ? "Available"
                      : "UnAvailable"}
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
                  <h3 className="text-base font-bold font-medium mb-2">
                    Seller
                  </h3>
                  <div className="flex items-center gap-2">
                    <div>
                      <p className="font-medium">{product?.sellerName}</p>
                      <p className="text-gray-600 text-sm mt-2">
                        Seller since {product?.sellerJoiningDate || "2020"}
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
      </div>
    </div>
  );
};

export default ProductDetails;
