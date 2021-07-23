package threads.cookbook_java9.ch05_fork_join_framework.recipe01_creating_fork_join_pool.util

/**
 * This class generates a product list of a determined size.
 * Each product is initialized with a predefined name and price.
 */
class ProductListGenerator {

    /**
     * This method generates the list of products
     *
     * @param size the size of the product list
     * @return the generated list of products
     */
    fun generate(size: Int): MutableList<Product> {
        val ret = mutableListOf<Product>();

        repeat(size) {
            val product = Product("Product $it", 10.0);
            ret.add(product);
        }

        return ret;
    }
}