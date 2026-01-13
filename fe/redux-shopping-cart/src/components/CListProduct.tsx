import type { Product } from "../types/Product";
import { CProduct } from "./CProduct";

export const ListProduct = ({ products }: { products: Product[] }) => {
    return (
        <section className="space-y-4">
            <header className="flex items-center justify-between">
                <h2 className="text-lg font-semibold text-slate-900">Products</h2>
                <span className="text-sm text-slate-500">{products.length} items</span>
            </header>
            <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
                {products.map((product) => (
                    <CProduct key={product.id} product={product} />
                ))}
            </div>
        </section>
    );
};