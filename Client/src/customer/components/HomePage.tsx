import { HomeSectionCarousel } from "./HomeSectionCarousel"
import MainCarousel from "./MainCarousel"

export const HomePage = () => {
    return (<><div>
        <MainCarousel />
    </div>
        <div className="space-y-10 py-20 flex flex-col justify-center px-5 lg:px-10">
            {/* <HomeSectionCarousel data={[1, 1, 1, 1, 1]} sectionName={"Mens kurta"} /> */}
            {/* <HomeSectionCarousel data={[1, 1, 1, 1, 1]} sectionName={"Mens shoes"} />
            <HomeSectionCarousel data={[1, 1, 1, 1, 1]} sectionName={"Mens shirts"} />
            <HomeSectionCarousel data={[1, 1, 1, 1, 1]} sectionName={"Womens sarees"} />
            <HomeSectionCarousel data={[1, 1, 1, 1, 1]} sectionName={"Womens dress"} /> */}
        </div>
    </>
    )
}