import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";


function ViewOrders() {
  const [orders, setOrders] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    // Fetch all orders
    const fetchOrders = async () => {
      try {
        const response = await axios.get("http://localhost:8080/admin/orders/all");
        setOrders(response.data);
      } catch (error) {
        console.error("There was an error fetching the orders!", error);
      }
    };

    fetchOrders();
  }, []);

  const handleOrderClick = (orderId) => {
    navigate(`/admin-moderation/orders/${orderId}`);
  };

  const formatDate = (dateArray) => {
    if (Array.isArray(dateArray) && dateArray.length === 5) {
      const [year, month, day, hour, minute] = dateArray;
      return new Date(year, month - 1, day, hour, minute).toLocaleDateString();
    }
    return "Invalid Date";
  };

  return (
    <div className="flex min-h-screen w-full overflow-hidden">
      <div className="flex flex-col flex-1">
        <header className="flex h-14 lg:h-[60px] items-center gap-4 border-b bg-gray-200 px-6">
          <div className="w-full flex-1">
            <h1 className="text-3xl font-bold">Orders</h1>
          </div>
        </header>
        <main className="flex flex-1 flex-col gap-4 p-4 md:gap-8 md:p-6">
          <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
            {orders.map((order) => (
              <div
                key={order.orderId}
                onClick={() => handleOrderClick(order.orderId)}
                className="cursor-pointer border border-gray-300 rounded-lg p-6 bg-white text-black hover:shadow-lg transition-shadow"
              >
                <div className="mb-4">
                  <h2 className="text-lg font-bold text-black">Order #{order.orderId}</h2>
                  <p className="text-gray-500">{order.buyer.fullName} - {formatDate(order.transactionDatetime)}</p>
                </div>
                <div className="flex items-center justify-between">
                  <div className="text-gray-500">
                    {order.productId.title} - {order.orderStatus}
                  </div>
                  <div className="font-medium">${order.totalAmount.toFixed(2)}</div>
                </div>
              </div>
            ))}
          </div>
        </main>
      </div>
    </div>
  );
}

export default ViewOrders;
