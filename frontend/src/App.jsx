import "./App.css";
import MenuCardList from "./components/MenuCardList";
import { useEffect, useState } from "react";
import CartItem from "./components/CartItem";
import OrderCard from "./components/OrderCard";

function App() {
    const [cart, setCart] = useState([]);
    const [tableId, setTableId] = useState(0);
    const [orderTrigger, setOrderTrigger] = useState(false);
    const [orderItemArray, setOrderItemArray] = useState([]);
    const totalPrice = cart.reduce(
        (sum, item) => sum + item.price * item.quantity,
        0
    );

    useEffect(() => {
        setTableId(
            parseInt(new URLSearchParams(window.location.search).get("table"))
        );
    }, []);

    const fetchData = async () => {
        const url = "http://localhost:8080/order";

        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error(`Response status: ${response.status}`);
            }

            const jsonData = await response.json();
            const dataArray = Object.values(jsonData);
            const ourTableOrder = dataArray.filter(
                (item) => item.tableId === tableId
            );
            console.log("Ourtable", ourTableOrder);
            setOrderItemArray(ourTableOrder);
        } catch (error) {
            console.log(error.message);
        }
    };

    useEffect(() => {
        fetchData();
    }, [tableId, orderTrigger]);

    function addToCart(item) {
        setCart((prevCart) => {
            const existingItem = prevCart.find(
                (cartItem) => cartItem.id === item.id
            );
            if (existingItem) {
                return prevCart.map((cartItem) =>
                    cartItem.id === item.id
                        ? { ...cartItem, quantity: cartItem.quantity + 1 }
                        : cartItem
                );
            }
            return [...prevCart, { ...item, quantity: 1 }];
        });
    }

    function increaseQuantity(id) {
        setCart((prevCart) => {
            const existingItem = prevCart.find(
                (cartItem) => cartItem.id === id
            );
            if (existingItem) {
                return prevCart.map((cartItem) =>
                    cartItem.id === id
                        ? { ...cartItem, quantity: cartItem.quantity + 1 }
                        : cartItem
                );
            }
        });
    }

    function reduceQuantity(id) {
        setCart((prevCart) => {
            const existingItem = prevCart.find(
                (cartItem) => cartItem.id === id
            );
            if (existingItem) {
                if (existingItem.quantity === 1) {
                    return prevCart.filter((item) => item.id !== id);
                }
                return prevCart.map((cartItem) =>
                    cartItem.id === id
                        ? { ...cartItem, quantity: cartItem.quantity - 1 }
                        : cartItem
                );
            }
        });
    }

    function removeItem(id) {
        setCart((prevCart) => prevCart.filter((item) => item.id !== id));
    }

    const placeOrder = async () => {
        const url = "http://localhost:8080/order";
        const orderItems = {
            tableId: tableId,
            orderItems: cart.map((orderItem) => ({
                id: orderItem.id,
                quantity: orderItem.quantity,
            })),
        };

        try {
            const response = await fetch(url, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(orderItems),
            });

            if (!response.ok) {
                console.error("Failed to place order");
                return;
            } else {
                console.log("Order placed successfully!");
            }
        } catch (error) {
            console.error(error);
        }

        console.log("Order data that would be sent:", orderItems);
        setCart([]);
        setOrderTrigger((prevOrderTrigger) => !prevOrderTrigger);
    };

    return (
        <div>
            <div className="w-full px-10 grid grid-cols-3 gap-10 box-border">
                <div className="col-span-2">
                    <MenuCardList addToCart={addToCart} />
                </div>

                <div className="col-span-1 relative">
                    <div className="sticky top-0 pt-10 grid grid-rows-2 h-screen gap-6">
                        <div className="row-span-1 relative overflow-hidden">
                            <h1 className="text-4xl font-serif">Cart</h1>
                            <ul className="cart-items w-full mt-6 flex flex-col gap-4 overflow-scroll h-full pb-40">
                                {!cart.length ? (
                                    <p className="text-center">Cart is empty</p>
                                ) : (
                                    cart.map((cartItem) => (
                                        <CartItem
                                            key={cartItem.id}
                                            id={cartItem.id}
                                            name={cartItem.name}
                                            description={cartItem.description}
                                            category={cartItem.category}
                                            price={cartItem.price}
                                            quantity={cartItem.quantity}
                                            increaseQuantity={increaseQuantity}
                                            reduceQuantity={reduceQuantity}
                                            removeItem={removeItem}
                                        />
                                    ))
                                )}
                            </ul>
                            {cart.length === 0 ? (
                                <div></div>
                            ) : (
                                <div className="w-full bg-white pt-6 z-10 absolute bottom-0">
                                    <button
                                        onClick={placeOrder}
                                        className="w-full bg-green-800 text-white h-10 rounded-full hover:bg-green-700 active:bg-green-900"
                                    >
                                        Total: ${totalPrice.toFixed(2)}
                                    </button>
                                </div>
                            )}
                        </div>
                        <div className="row-span-1 relative overflow-hidden">
                            <h1 className="text-4xl font-serif">Orders</h1>
                            <div className="w-full mt-6 flex flex-col gap-2 overflow-scroll h-full pb-20">
                                {Array.isArray(orderItemArray) &&
                                    orderItemArray.map((item, index) => (
                                        <OrderCard
                                            key={item.id}
                                            id={item.id}
                                            orderItems={item.orderItems}
                                            status={item.orderStatus}
                                            totalPrice={item.totalPrice}
                                            onDelete={() =>
                                                setOrderTrigger((prev) => !prev)
                                            }
                                        />
                                    ))}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default App;
