import React from "react";
import { Link } from "react-router-dom"; // If using React Router for navigation

export default function PaymentCancelled() {
  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100 px-4 py-12 sm:px-6 lg:px-8">
      <div className="max-w-md mx-auto text-center">
        <CircleXIcon className="mx-auto h-12 w-12 text-red-500" />
        <h1 className="mt-4 text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">Payment Cancelled</h1>
        <p className="mt-4 text-gray-600">Your payment has been cancelled. Please try again later.</p>
        <div className="mt-6 flex justify-center gap-2">
          <Link
            to="/"
            className="inline-flex items-center rounded-md bg-blue-600 px-4 py-2 text-sm font-medium text-white shadow-sm transition-colors hover:bg-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:ring-offset-2"
          >
            Go to Homepage
          </Link>
          <button
            className="inline-flex items-center rounded-md border border-gray-300 px-4 py-2 text-sm font-medium text-gray-700 transition-colors hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:ring-offset-2"
          >
            Try Again
          </button>
        </div>
      </div>
    </div>
  );
}

function CircleXIcon(props) {
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
      <circle cx="12" cy="12" r="10" />
      <path d="M15 9l-6 6" />
      <path d="M9 9l6 6" />
    </svg>
  );
}
