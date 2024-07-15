import React, { useEffect, useState } from "react";
import axios from "axios";
import Header from "../../components/Header";
import { useNavigate } from "react-router-dom";
import DataNotFound from "../../components/DataNotFound";
import Loader from "../../components/Loader";
import SubHeader from "../../components/SubHeader";

export default function Reviews() {
  const navigate = useNavigate();
  const userid = 1;

  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(false); // Add loading state

  useEffect(() => {
    const fetchProductsRatings = async () => {
      try {
        setLoading(true); // Set loading to true before fetching
        const params = new URLSearchParams(window.location.search);
        const productId = params.get("id");
        console.log({ productId });

        const response = await axios.get(
          `http://localhost:8080/product_ratings/${userid}`,
          {
            params: params,
            paramsSerializer: { indexes: null },
          }
        );
        const data = response.data;
        setReviews(data);
        console.log(data, "product_ratings");
      } catch (error) {
        console.error("Failed to fetch product data", error);
        // setError(error);
        // setLoading(false);
      } finally {
        setTimeout(() => {
          setLoading(false);
        }, 800); // Set loading to false after fetching
      }
    };
    fetchProductsRatings();
  }, []);

  return (
    <div className="bg-gray-100 dark:bg-gray-950 py-8 h-screen max-h-100">
      <Header />
      <SubHeader title={'Reviews'} backPath={'/profile'} />
      
      <div className="py-8 mt-50">
        {loading ? ( // Conditionally render the loading indicator
          <div className="my-50">
            <Loader />
          </div>
        ) : reviews.length === 0 ? (
          <div>
            {" "}
            <DataNotFound message={"Oops! No reviews found."} />
          </div>
        ) : (
          <div className="border rounded-lg shadow-sm dark:border-gray-800 m-4">          
            <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400 dark:text-white">
              <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                <tr>
                  <th className="px-6 py-3">No.</th>

                  <th className="px-6 py-3">Title</th>
                  <th className="px-6 py-3">Review</th>
                  <th className="px-6 py-3">Rating</th>
                </tr>
              </thead>
              <tbody>
                {reviews.map((item, index) => (
                  <tr
                    className="bg-white border-b dark:bg-gray-800 dark:border-gray-700"
                    key={index}
                  >
                    <td className="px-6 py-4">{index + 1}</td>

                    <td className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                      <div className="flex items-center gap-3">
                        {/* <img
                    alt="Sold Item"
                    className="rounded-lg object-cover"
                    height={50}
                    src="/placeholder.png"
                    style={{
                      aspectRatio: "50/50",
                      objectFit: "cover",
                    }}
                    width={50}
                  /> */}
                        <div>{item?.title}</div>
                      </div>
                    </td>
                    <td className="px-6 py-4">{item?.review}</td>

                    <td className="px-6 py-3">
                      <div className="flex items-center gap-px">
                        <StarRating starCount={item?.rating} />
                      </div>
                    </td>
                  </tr>
                ))}
                {/* <tr className="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
              <td className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                <div className="flex items-center gap-3">
                  <img
                    alt="Sold Item"
                    className="rounded-lg object-cover"
                    height={50}
                    src="/placeholder.png"
                    style={{
                      aspectRatio: "50/50",
                      objectFit: "cover",
                    }}
                    width={50}
                  />
                  <div>Retro Sunglasses</div>
                </div>
              </td>
              <td className="px-6 py-4">Very good</td>
            
              <td className="px-6 py-3">
                <div className="flex items-center gap-px">
                  <StarIcon className="w-4 h-4 fill-primary" />
                  <StarIcon className="w-4 h-4 fill-primary" />
                  <StarIcon className="w-4 h-4 fill-primary" />
                  <StarIcon className="w-4 h-4 fill-primary" />
                  <StarIcon className="w-4 h-4 fill-muted stroke-muted-foreground" />
                </div>
              </td>
            </tr>
            <tr className="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
              <td className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                <div className="flex items-center gap-3">
                  <img
                    alt="Sold Item"
                    className="rounded-lg object-cover"
                    height={50}
                    src="/placeholder.png"
                    style={{
                      aspectRatio: "50/50",
                      objectFit: "cover",
                    }}
                    width={50}
                  />
                  <div>Vintage Denim Jacket</div>
                </div>
              </td>
              <td className="px-6 py-4">Average</td>
              
              <td className="px-6 py-3">
                <div className="flex items-center gap-px">
                  <StarIcon className="w-4 h-4 fill-primary" />
                  <StarIcon className="w-4 h-4 fill-primary" />
                  <StarIcon className="w-4 h-4 fill-primary" />
                  <StarIcon className="w-4 h-4 fill-muted stroke-muted-foreground" />
                  <StarIcon className="w-4 h-4 fill-muted stroke-muted-foreground" />
                </div>
              </td>
            </tr>
            <tr className="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
              <td className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                <div className="flex items-center gap-3">
                  <img
                    alt="Sold Item"
                    className="rounded-lg object-cover"
                    height={50}
                    src="/placeholder.png"
                    style={{
                      aspectRatio: "50/50",
                      objectFit: "cover",
                    }}
                    width={50}
                  />
                  <div>Vintage Leather Bag</div>
                </div>
              </td>
              <td className="px-6 py-4">Good</td>
              
              <td className="px-6 py-3">
                <div className="flex items-center gap-px">
                  <StarIcon className="w-4 h-4 fill-primary" />
                  <StarIcon className="w-4 h-4 fill-primary" />
                  <StarIcon className="w-4 h-4 fill-primary" />
                  <StarIcon className="w-4 h-4 fill-primary" />
                  <StarIcon className="w-4 h-4 fill-muted stroke-muted-foreground" />
                </div>
              </td>
            </tr> */}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}

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
