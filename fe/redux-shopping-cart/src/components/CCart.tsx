import { useSelector } from "react-redux";
import { CCartItem } from "./CCartItem";

export const CCart = () => {
    const selector = useSelector((state: any) => state.cart);

    return (
        <div>
            <h2 className="text-2xl font-bold mb-4">Shopping Cart</h2>
            {/* Cart items will be listed here */}
            {selector.map((item: any) => (
                <CCartItem key={item.id} item={item} />
            ))}
        </div>
    );
}