import unittest
from src.models.menu_item import MenuItem

class TestMenuItem(unittest.TestCase):

    def setUp(self):
        self.item = MenuItem(nombre="Pizza", precio=10.0, categoria="Plato Principal")

    def test_get_item_details(self):
        expected_details = "Pizza - $10.0 - Plato Principal"
        self.assertEqual(self.item.get_item_details(), expected_details)

    def test_calculate_price_with_tax(self):
        expected_price = 10.0 * 1.21  # Assuming IVA is 21%
        self.assertEqual(self.item.calculate_price_with_tax(), expected_price)

if __name__ == '__main__':
    unittest.main()