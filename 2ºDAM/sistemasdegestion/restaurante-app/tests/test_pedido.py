import unittest
from src.models.pedido import Pedido
from src.models.menu_item import MenuItem

class TestPedido(unittest.TestCase):

    def setUp(self):
        self.pedido = Pedido()
        self.item1 = MenuItem("Pizza", 10.0, "Plato Principal")
        self.item2 = MenuItem("Ensalada", 5.0, "Entrante")

    def test_add_item(self):
        self.pedido.add_item(self.item1)
        self.assertIn(self.item1, self.pedido.items)

    def test_calculate_total(self):
        self.pedido.add_item(self.item1)
        self.pedido.add_item(self.item2)
        total = self.pedido.calculate_total()
        self.assertEqual(total, 15.0)

    def test_calculate_total_with_iva(self):
        self.pedido.add_item(self.item1)
        total_with_iva = self.pedido.calculate_total_with_iva()
        self.assertEqual(total_with_iva, 12.1)  # Assuming IVA is 21%

    def test_summary(self):
        self.pedido.add_item(self.item1)
        self.pedido.add_item(self.item2)
        summary = self.pedido.summary()
        expected_summary = "Pedido:\n- Pizza: 10.0\n- Ensalada: 5.0\nTotal: 15.0"
        self.assertEqual(summary, expected_summary)

if __name__ == '__main__':
    unittest.main()