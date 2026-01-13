import { useDispatch } from "react-redux";
import { fetchProductId } from "../feature/cart/cartSlice";
import type { Product } from "../types/Product";
export const CProduct = ({ product }: { product: Product }) => {
    const dispatch = useDispatch<any>();

    return (
        <article className="flex h-full flex-col overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm transition hover:-translate-y-1 hover:shadow-lg">
            <figure className="relative aspect-[4/3] overflow-hidden">
                <img
                    src={product.image}
                    alt={product.title}
                    loading="lazy"
                    className="h-full w-full object-cover transition duration-300 group-hover:scale-105"
                />
            </figure>
            <div className="flex flex-1 flex-col gap-3 p-5">
                <h3 className="text-lg font-semibold text-slate-900 line-clamp-2">{product.title}</h3>
                <p className="text-sm text-slate-600 line-clamp-3">{product.description}</p>
            </div>
            <footer className="flex items-center justify-between border-t border-slate-200 px-5 py-4">
                <span className="text-lg font-semibold text-slate-900">${product.price.toFixed(2)}</span>
                <button
                    className="rounded-lg bg-blue-600 px-4 py-2 text-sm font-semibold shadow-sm transition hover:bg-blue-500 focus:outline-none focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-2"
                    onClick={() => dispatch(fetchProductId(product.id))}
                >Add to Cart
                </button>
            </footer>
        </article >
    );
}