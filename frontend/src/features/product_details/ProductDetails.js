import React, { useState, useEffect } from "react";
import Header from "../../components/AppHeader";
import axios from "axios";
import placeholder from "../../assets/images/placeholder.png";

const ProductDetails = () => {
  const userid = 1;

  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  function capitalizeFirstLetter(str) {
    if (!str) return "";
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
  }

  async function addtoFavorite(userid, productId) {
    const headers = {
      "Content-Type": "application/json",
    };
    try {
      const response = await axios.post(
        `http://localhost:8080/${userid}/${productId}/favorite`,
        headers
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
        const params = new URLSearchParams(window.location.search);
        const productId = params.get("id");
        console.log({ productId });
        //const userid = params.get("userid")
        //console.log({userid})
        const response = await axios.get(`http://localhost:8080/${userid}/${productId}`, { 
          params: params,
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
  }, []);

  return (
    !loading && <div>
      <Header />
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
                    size="sm"
                    variant="outline"
                    className="flex flex-end gap-2 bg-transparent hover:bg-neutral-900 text-700 font-semibold hover:text-white py-2 px-4 border border-500 hover:border-transparent rounded"
                    onClick={() => addtoFavorite(1, product?.productId)}
                  >
                    {product.favorite ? <FilledHeartIcon /> : <HeartIcon />}
                    Favorite
                  </button>
                  <button
                    size="sm"
                    variant="outline"
                    className="bg-transparent hover:bg-neutral-900 text-700 font-semibold hover:text-white py-2 px-4 border border-500 hover:border-transparent rounded"
                  >
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
                        {/* <StarIcon className="w-3.5 h-5 fill-primary" />
                        <StarIcon className="w-3.5 h-5 fill-primary" />
                        <StarIcon className="w-3.5 h-5 fill-primary" />
                        <StarIcon className="w-3.5 h-5 fill-primary" />
                        <StarIcon className="w-3.5 h-5 fill-muted" /> */}
                        <StarRating starCount={3} />
                        {/* <span className="text-gray-500  dark:text-gray-400">
                          (745)
                        </span> */}
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

const HeartIcon = (props) => {
  return (
    <svg
      {...props}
      xmlns="http://www.w3.org/2000/svg"
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <path d="M19 14c1.49-1.46 3-3.21 3-5.5A5.5 5.5 0 0 0 16.5 3c-1.76 0-3 .5-4.5 2-1.5-1.5-2.74-2-4.5-2A5.5 5.5 0 0 0 2 8.5c0 2.3 1.5 4.05 3 5.5l7 7Z" />
    </svg>
  );
};
const FilledHeartIcon = (props) => {
  return (
    <svg
      {...props}
      xmlns="http://www.w3.org/2000/svg"
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="currentColor"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <path d="M19 14c1.49-1.46 3-3.21 3-5.5A5.5 5.5 0 0 0 16.5 3c-1.76 0-3 .5-4.5 2-1.5-1.5-2.74-2-4.5-2A5.5 5.5 0 0 0 2 8.5c0 2.3 1.5 4.05 3 5.5l7 7Z" />
    </svg>
  );
};

const StarIcon = (props) => {
  return (
    <svg
      {...props}
      xmlns="http://www.w3.org/2000/svg"
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2" />
    </svg>
  );
};

const FilledStarIcon = (props) => {
  return (
    <svg
      {...props}
      xmlns="http://www.w3.org/2000/svg"
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="currentColor"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2" />
    </svg>
  );
};

const StarRating = ({ starCount }) => {
  const totalStars = 5;
  const stars = [];

  for (let i = 0; i < totalStars; i++) {
    if (i < starCount) {
      stars.push(<FilledStarIcon key={i} />);
    } else {
      stars.push(<StarIcon key={i} />);
    }
  }

  return <div className="flex flex-row">{stars}</div>;
};

export default ProductDetails;
