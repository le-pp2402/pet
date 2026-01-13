import './App.css'
import { CCart } from './components/CCart';
import { ListProduct } from './components/CListProduct';

const mockProducts = [
  {
    id: 1,
    title: "Product 1",
    price: 29.99,
    description: "This is the description for Product 1.",
    image: "https://picsum.photos/200/300"
  },
  {
    id: 2,
    title: "Product 2",
    price: 49.99,
    description: "This is the description for Product 2.",
    image: "https://picsum.photos/200/300"
  },
  {
    id: 3,
    title: "Product 3",
    price: 19.99,
    description: "This is the description for Product 3.",
    image: "https://picsum.photos/200/300"
  }
];

function App() {
  return (
    <>
      <CCart />
      <ListProduct products={mockProducts} />
    </>
  )
}

export default App
