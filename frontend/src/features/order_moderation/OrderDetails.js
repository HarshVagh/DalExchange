import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

function OrderDetails() {
  const { orderId } = useParams();
  const [order, setOrder] = useState(null);
  const [orderStatus, setOrderStatus] = useState("");
  const [shippingAddress, setShippingAddress] = useState({
    billingName: "",
    country: "",
    line1: "",
    line2: "",
    city: "",
    state: "",
    postalCode: ""
  });
  const [adminComments, setAdminComments] = useState("");
  const [message, setMessage] = useState("");
  const [showMessage, setShowMessage] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchOrderDetails = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/admin/orders/orderDetails/${orderId}`);
        const orderData = response.data;
        setOrder(orderData);
        setOrderStatus(orderData.orderStatus);
        setShippingAddress(orderData.shippingAddress || {
          billingName: "",
          country: "",
          line1: "",
          line2: "",
          city: "",
          state: "",
          postalCode: ""
        });
        setAdminComments(orderData.adminComments || "");
      } catch (error) {
        showMessageWithFade("There was an error fetching the order details.");
      }
    };

    fetchOrderDetails();
  }, [orderId]);

  const handleSaveChanges = async () => {
    try {
      const updatedOrder = {
        shippingAddress,
        orderStatus,
        adminComments
      };
      await axios.put(`http://localhost:8080/admin/orders/update/${orderId}`, updatedOrder);

      showMessageWithFade("Order and shipping address updated successfully");
      navigate("/admin-moderation/orders");
    } catch (error) {
      showMessageWithFade("There was an error updating the order or shipping address.");
    }
  };

  const handleRefundOrder = async () => {
    if (order.totalAmount === 0) {
      showMessageWithFade("Order has already been refunded.");
      return;
    }

    try {
      await axios.put(`http://localhost:8080/admin/orders/refund/${orderId}`, order.totalAmount.toString(), {
        headers: {
          'Content-Type': 'text/plain'
        }
      });
      showMessageWithFade("Order refunded successfully");
      navigate("/admin-moderation/orders");
    } catch (error) {
      showMessageWithFade("There was an error processing the refund.");
    }
  };

  const showMessageWithFade = (msg) => {
    setMessage(msg);
    setShowMessage(true);
    setTimeout(() => {
      setShowMessage(false);
    }, 5000);
  };

  if (!order) return <div>Loading...</div>;

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setShippingAddress({
      ...shippingAddress,
      [name]: value
    });
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
            <h1 className="text-3xl font-bold">Order Details</h1>
          </div>
        </header>
        <main className="flex flex-1 flex-col gap-4 p-4 md:gap-8 md:p-6">
          {showMessage && (
            <div className="fixed top-0 left-0 right-0 bg-black text-white p-4 z-50 flex justify-between items-center">
              <span>{message}</span>
              <button
                className="text-white bg-transparent border border-white rounded px-2 py-1"
                onClick={() => setShowMessage(false)}
              >
                Close
              </button>
            </div>
          )}
          <div className="grid md:grid-cols-2 gap-6">
            <div className="border border-gray-300 rounded-lg bg-white text-black p-6">
              <div className="mb-4">
                <h2 className="text-lg font-bold text-black border-b pb-2">View Order Details</h2>
              </div>
              <div className="grid gap-4">
                <div className="grid gap-1">
                  <div className="text-black font-bold">Customer</div>
                  <div className="font-normal">{order.buyer.username}</div>
                </div>
                <div className="grid gap-1">
                  <div className="text-black font-bold">Product</div>
                  <div className="font-normal">{order.productId.title}</div>
                </div>
                <div className="grid gap-1">
                  <div className="text-black font-bold">Order Date</div>
                  <div className="font-normal">{formatDate(order.transactionDatetime)}</div>
                </div>
                <div className="grid gap-1">
                  <div className="text-black font-bold">Order Total</div>
                  <div className="font-normal">${order.totalAmount.toFixed(2)}</div>
                </div>
                <div className="grid gap-1">
                  <div className="text-black font-bold">Shipping Address</div>
                  <address className="not-italic font-normal">
                    {`${shippingAddress.billingName}, ${shippingAddress.line1}, ${shippingAddress.line2}, ${shippingAddress.city}, ${shippingAddress.state}, ${shippingAddress.country}, ${shippingAddress.postalCode}`}
                  </address>
                </div>
                <div className="grid gap-1">
                  <div className="text-black font-bold">Admin Comments</div>
                  <div className="font-normal">{adminComments}</div>
                </div>
              </div>
            </div>
            <div className="border border-gray-300 rounded-lg bg-white text-black p-6">
              <div className="mb-4">
                <h2 className="text-lg font-bold text-black border-b pb-2">Edit Order Details</h2>
              </div>
              <form className="grid gap-4">
                <div className="grid gap-2">
                  <label htmlFor="order-status" className="font-bold">Order Status</label>
                  <select
                    id="order-status"
                    className="w-full p-2 border border-gray-300 rounded-lg"
                    value={orderStatus}
                    onChange={(e) => setOrderStatus(e.target.value)}
                  >
                    <option value="PENDING">Pending</option>
                    <option value="SHIPPED">Shipped</option>
                    <option value="DELIVERED">Delivered</option>
                    <option value="CANCELLED">Cancelled</option>
                  </select>
                </div>
                <div className="grid gap-2">
                  <label htmlFor="billingName" className="font-bold">Billing Name</label>
                  <input
                    id="billingName"
                    name="billingName"
                    className="w-full p-2 border border-gray-300 rounded-lg"
                    value={shippingAddress.billingName}
                    onChange={handleInputChange}
                  />
                </div>
                <div className="grid gap-2">
                  <label htmlFor="country" className="font-bold">Country</label>
                  <input
                    id="country"
                    name="country"
                    className="w-full p-2 border border-gray-300 rounded-lg"
                    value={shippingAddress.country}
                    onChange={handleInputChange}
                  />
                </div>
                <div className="grid gap-2">
                  <label htmlFor="line1" className="font-bold">Address Line 1</label>
                  <input
                    id="line1"
                    name="line1"
                    className="w-full p-2 border border-gray-300 rounded-lg"
                    value={shippingAddress.line1}
                    onChange={handleInputChange}
                  />
                </div>
                <div className="grid gap-2">
                  <label htmlFor="line2" className="font-bold">Address Line 2</label>
                  <input
                    id="line2"
                    name="line2"
                    className="w-full p-2 border border-gray-300 rounded-lg"
                    value={shippingAddress.line2}
                    onChange={handleInputChange}
                  />
                </div>
                <div className="grid gap-2">
                  <label htmlFor="city" className="font-bold">City</label>
                  <input
                    id="city"
                    name="city"
                    className="w-full p-2 border border-gray-300 rounded-lg"
                    value={shippingAddress.city}
                    onChange={handleInputChange}
                  />
                </div>
                <div className="grid gap-2">
                  <label htmlFor="state" className="font-bold">State</label>
                  <input
                    id="state"
                    name="state"
                    className="w-full p-2 border border-gray-300 rounded-lg"
                    value={shippingAddress.state}
                    onChange={handleInputChange}
                  />
                </div>
                <div className="grid gap-2">
                  <label htmlFor="postalCode" className="font-bold">Postal Code</label>
                  <input
                    id="postalCode"
                    name="postalCode"
                    className="w-full p-2 border border-gray-300 rounded-lg"
                    value={shippingAddress.postalCode}
                    onChange={handleInputChange}
                  />
                </div>
                <div className="grid gap-2">
                  <label htmlFor="admin-comments" className="font-bold">Admin Comments</label>
                  <textarea
                    id="admin-comments"
                    className="w-full p-2 border border-gray-300 rounded-lg"
                    rows={3}
                    value={adminComments}
                    onChange={(e) => setAdminComments(e.target.value)}
                    placeholder="Add any admin comments here..."
                  />
                </div>
                <div className="flex gap-2">
                  <button
                    type="button"
                    className="px-4 py-2 bg-black text-white rounded hover:bg-gray-700 transition"
                    onClick={handleSaveChanges}
                  >
                    Save Changes
                  </button>
                  <button
                    type="button"
                    className="px-4 py-2 bg-transparent text-black border border-black rounded hover:bg-black hover:text-white transition"
                    onClick={handleRefundOrder}
                  >
                    Refund Order
                  </button>
                </div>
              </form>
            </div>
          </div>
        </main>
      </div>
    </div>
  );
}

export default OrderDetails;
