import SearchInput from "../components/Search/SearchInput";
import HomeForm from "../components/HomeForm";
import ImageSlider from "../components/ImageSlider";
const images = [];
function Home() {
  return (
    <div>
      <ImageSlider />
      <SearchInput />
      <HomeForm />
    </div>
  );
}
export default Home;
