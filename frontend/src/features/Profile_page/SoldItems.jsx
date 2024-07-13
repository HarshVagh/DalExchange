import React, {useEffect, useState} from "react";
import axios from "axios";
import Header from "../../components/AppHeader";
import { useNavigate } from "react-router-dom";
import DataNotFound from "../../components/DataNotFound";
import Loader from "../../components/Loader"



export default function SoldItems() {
  const navigate = useNavigate();
  const userid = 1;
//   {
//     "soldItemId": 1,
//     "title": "Laptop",
//     "price": 999.99,
//     "soldDate": null
// }


  const [soldItems, setSoldItems] = useState([])
  const [loading, setLoading] = useState(false); // Add loading state

  useEffect(() => {
    const fetchSoldItems = async () => {
      try {
        const params = new URLSearchParams(window.location.search);
        const productId = params.get("id");
        console.log({ productId });
        //const userid = params.get("userid")
        //console.log({userid})
        setLoading(true); // Set loading to true before fetching

        const response = await axios.get(`http://localhost:8080/sold_products/${userid}`, { 
          params: params,
          paramsSerializer: {indexes: null }
        });
        const data = response.data;
        setSoldItems(data)
        console.log(data, "sold_products");
        // setProduct(data);
        // setLoading(false);
      } catch (error) {
        console.error("Failed to fetch product data", error);
        // setError(error);
        // setLoading(false);
      }finally {
        setTimeout(()=>{
         setLoading(false)
        },800)
       ; // Set loading to false after fetching
     }
    };
    fetchSoldItems();
  }, []);

  return (
    <>
      <div className="bg-gray-100 dark:bg-gray-950 py-8 h-screen max-h-100">
      <Header />
        <div className="py-2 px-4">
          <button
            type="button"
            class="text-white bg-black border border-gray-300 focus:outline-none hover:bg-white hover:text-black focus:ring-4 focus:ring-gray-100 font-medium rounded-lg text-sm px-5 py-2.5 my-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"
            onClick={() => {
              navigate("/profile");
            }}
          >
            Back to Profile
          </button>
        </div>
        <div className="flex items-center justify-between mb-4 px-4">
          <h3 className="text-lg font-semibold dark:text-white">Sold Items</h3>
          {/* <button className="dark:text-white" size="sm" variant="link">
            View More
          </button> */}
        </div>
       { loading ? ( // Conditionally render the loading indicator
          <div className="my-50">
           <Loader/>
          </div>
        ) :soldItems.length === 0 ? 
       
       <div className="my-20"> <DataNotFound message={"Oops! No items sold yet."}/></div>
       :
       
       <div className="border rounded-lg shadow-sm dark:border-gray-800 mx-4">
          <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400 dark:text-white">
            <thead className="px-4 text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
              <tr>
              <th className="px-6 py-3">Id</th>

                <th className="px-6 py-3">Title</th>
                <th className="px-6 py-3">Price</th>
                <th className="px-6 py-3">Date</th>
                {/* <th className="px-6 py-3">Profit</th>
                <th className="px-6 py-3">Average Review</th> */}
              </tr>
            </thead>
            <tbody>
              {soldItems.map((item,index)=>(
                <tr className="bg-white border-b dark:bg-gray-800 dark:border-gray-700" key={index}>
                <td className="px-6 py-4">{item.soldItemId}</td>

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
                    <div>{item.title}</div>
                  </div>
                </td>
                <td className="px-6 py-4">{item.price}</td>
                <td className="px-6 py-3">$9.01</td>
                {/* <td className="px-6 py-3">
                  <div className="flex items-center gap-px">
                    <StarIcon className="w-4 h-4 fill-primary" />
                    <StarIcon className="w-4 h-4 fill-primary" />
                    <StarIcon className="w-4 h-4 fill-primary" />
                    <StarIcon className="w-4 h-4 fill-muted stroke-muted-foreground" />
                    <StarIcon className="w-4 h-4 fill-muted stroke-muted-foreground" />
                  </div>
                </td> */}
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
                <td className="px-6 py-4">$35.00</td>
                <td className="px-6 py-4">April 15, 2023</td>
                <td className="px-6 py-3">$10.01</td>
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
                <td className="px-6 py-4">$49.00</td>
                <td className="px-6 py-4">March 20, 2023</td>
                <td className="px-6 py-3">$9.01</td>
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
                <td className="px-6 py-4">$69.00</td>
                <td className="px-6 py-4">February 28, 2023</td>
                <td className="px-6 py-3">$9.01</td>
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
        </div>}
        {/* <div className="mt-4 text-right mx-4">
          <div className="bg-gray-200 dark:bg-gray-800 px-3 py-1 rounded-full text-sm font-medium text-gray-700 dark:text-gray-300">
            Total Profit: $37.04
          </div>
        </div> */}
      </div>
    </>
  );
}

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
