import "./App.css";
import MenuCardList from "./components/MenuCardList";
import { useEffect, useState } from "react";
import CartItem from "./components/CartItem";

function App() {
    const [cart, setCart] = useState([]);
    const totalPrice = cart.reduce(
        (sum, item) => sum + item.price * item.quantity,
        0
    );

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

    function placeOrder() {
        const url = "http://localhost:8080/order";
        const orderItem = {
            items: cart,
            totalAmount: totalPrice,
            orderDate: new Date().toISOString(),
        };

        try {
            const response = fetch(url, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(orderItem),
            });

            if (!response.ok) {
                console.error("Failed to place order");
            } else {
                console.log("Order placed successfully!");
            }
        } catch (error) {
            console.error(error);
        }

        console.log("Order data that would be sent:", orderItem);
        setCart([]);
    }

    return (
        <div>
            <div className="w-full px-10 pt-10 grid grid-cols-3 gap-10 box-border">
                <div className="col-span-2">
                    <MenuCardList addToCart={addToCart} />
                </div>

                <div className="col-span-1 relative">
                    <div className="cart fixed top-0 flex flex-col min-h-[calc(100vh-4rem)]">
                        <h1 className="text-4xl font-serif">Cart</h1>
                        <ul className="cart-items w-full mt-6 flex flex-col gap-4 flex-grow">
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
                            <button
                                onClick={placeOrder}
                                className="w-full bg-green-800 text-white h-10 rounded-full hover:bg-green-700 active:bg-green-900"
                            >
                                Total: ${totalPrice.toFixed(2)}
                            </button>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default App;
