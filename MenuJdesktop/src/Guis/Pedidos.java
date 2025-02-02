package Guis;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.beans.PropertyVetoException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Conexao.Dao.ClienteDao;
import Conexao.Dao.PedidoDao;
import Conexao.Dao.ProdutoDao;
import Models.Cliente;
import Models.ItemPedido;
import Models.ListaPedido;
import Models.Pedido;
import Models.Produto;

import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import java.awt.Component;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

@SuppressWarnings("serial")
public class Pedidos extends JInternalFrame {
	private JTextField textCPFCliente;
	private JTextField textNomeCliente;
	private JTextField textSelCodProduto;
	private JTable tblProdutosVenda;
	private JTable tblProdutosSelecao;
	private JTextField textSelQtdItem;
	public JTextField textSelNomeProduto;
	private JTextField textQtdItens;
	private JTextField textValorTotal;
	Pedido pedido = new Pedido();
	public Produto teste;
	Double valorTotalPedido = 0.00;
	Produto produto = new Produto();
	DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
	DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
	DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
	DefaultTableCellRenderer backgroundVerde = new DefaultTableCellRenderer();
	DefaultTableCellRenderer fonteVermelha = new DefaultTableCellRenderer();

	DefaultTableModel mdlProdutosVda, mdlProdutosSel;
	private JTextField textCodCliente;
	private JTextField textSelValorVenda;
	private JTextField textSelDesMarca;
	private JTextField textSelCodMarca;
	private String tipoPedido = "";
	private JComboBox comboBoxCondicaoPagamento = new JComboBox();
	JRadioButton rdbtnOrcamento = new JRadioButton("Orçamento");
	JRadioButton rdbtnPedido = new JRadioButton("Pedido");
	JComboBox cmbBoxOrcamento = new JComboBox();
	JButton btnExcluiOrcamento = new JButton("Exclui Orçamento");
	boolean orcamentoRecuperado = false;
	String codigoOrcamentoBase = "";

	ProdutoDao produtodao = new ProdutoDao();
	PedidoDao pedidodao = new PedidoDao();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pedidos frame = new Pedidos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Pedidos() {
		try {
			setMaximum(false);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}

		centralizado.setHorizontalAlignment(SwingConstants.CENTER);
		esquerda.setHorizontalAlignment(SwingConstants.LEFT);
		direita.setHorizontalAlignment(SwingConstants.RIGHT);
		backgroundVerde.setBackground(Color.GREEN);
		fonteVermelha.setForeground(Color.RED);

		setClosable(true);
		setFrameIcon(new ImageIcon(Pedidos.class.getResource("/Icones/relatorio.png")));
		setTitle("Gestão de Pedidos");
		setBounds(100, 100, 770, 538);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Pedidos/Orçamentos");
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 24));
		lblNewLabel.setBounds(250, 0, 259, 48);
		getContentPane().add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 46, 739, 455);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblClienteCpf = new JLabel("Cliente CPF:");
		lblClienteCpf.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblClienteCpf.setBounds(23, 41, 94, 20);
		panel.add(lblClienteCpf);

		textCPFCliente = new JTextField();
		textCPFCliente.setEditable(false);
		textCPFCliente.setBackground(new Color(225, 225, 225));
		textCPFCliente.setBounds(99, 41, 120, 20);
		panel.add(textCPFCliente);
		textCPFCliente.setColumns(10);

		textNomeCliente = new JTextField();
		textNomeCliente.setEditable(false);
		textNomeCliente.setColumns(10);
		textNomeCliente.setBackground(new Color(225, 225, 225));
		textNomeCliente.setBounds(229, 41, 318, 20);
		panel.add(textNomeCliente);

		textSelCodProduto = new JTextField();
		textSelCodProduto.setEditable(false);
		textSelCodProduto.setColumns(10);
		textSelCodProduto.setBackground(new Color(225, 225, 225));
		textSelCodProduto.setBounds(107, 236, 67, 20);
		panel.add(textSelCodProduto);

		comboBoxCondicaoPagamento
				.setModel(new DefaultComboBoxModel(new String[] { "", "Dinheiro", "Pix", "Débito", "Crédito" }));
		comboBoxCondicaoPagamento.setBounds(179, 10, 94, 18);
		panel.add(comboBoxCondicaoPagamento);

		JLabel lblNewLabel_1 = new JLabel("Condição de Pagamento:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(23, 11, 173, 14);
		panel.add(lblNewLabel_1);

		JLabel lblQuantidade = new JLabel("Quantidade:");
		lblQuantidade.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblQuantidade.setBounds(467, 206, 80, 20);
		panel.add(lblQuantidade);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 267, 693, 110);
		panel.add(scrollPane);

		rdbtnPedido.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (cmbBoxOrcamento.getItemCount() > 0) {
					int codigoPedido = Integer.parseInt(cmbBoxOrcamento.getSelectedItem().toString());
					if (codigoPedido > 0) {
						if (JOptionPane.showConfirmDialog(null, "Confirma geração do Pedido a partir do Orçamento?",
								"SIM", JOptionPane.YES_NO_OPTION) == 0) {
							orcamentoRecuperado = true;
							rdbtnOrcamento.setSelected(false);
							rdbtnPedido.setSelected(true);
						} else {
							rdbtnPedido.setSelected(false);
							return;
						}
					}
				}

				if (rdbtnPedido.isSelected()) {
					cmbBoxOrcamento.removeAllItems();
					rdbtnOrcamento.setSelected(false);
					cmbBoxOrcamento.setVisible(false);

				}
				tipoPedido = "P";
				pedido.setTipo_pedido(tipoPedido);
				tipoPedido = "";
			}
		});

		rdbtnOrcamento.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				limpaCampos("O");
				if (rdbtnOrcamento.isSelected()) {
					cmbBoxOrcamento.setVisible(true);

					// Carrega dados comboBox orçamentos
					carregaComboBoxOrcamento();

					rdbtnPedido.setSelected(false);
				}
				tipoPedido = "O";
				pedido.setTipo_pedido(tipoPedido);
				tipoPedido = "";
			}
		});

		tblProdutosVenda = new JTable();
		scrollPane.setViewportView(tblProdutosVenda);
		tblProdutosVenda.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textSelQtdItem.setEditable(false);

				int contador = tblProdutosVenda.getSelectedRow();
				textSelCodProduto.setText(mdlProdutosVda.getValueAt(contador, 0).toString());
				textSelNomeProduto.setText(mdlProdutosVda.getValueAt(contador, 1).toString());
				textSelQtdItem.setText(mdlProdutosVda.getValueAt(contador, 2).toString());
				textSelCodMarca.setText(mdlProdutosVda.getValueAt(contador, 3).toString());
				textSelDesMarca.setText(mdlProdutosVda.getValueAt(contador, 4).toString());
				textSelValorVenda.setText(mdlProdutosVda.getValueAt(contador, 5).toString());

			}
		});

		mdlProdutosVda = new DefaultTableModel();
		Object[] colunn = { "Cod.Prod.", "Des.Produto", "Quantidade", "Marca", "Des.Marca", "Vlr.Venda", "Vlr.Tot.Item",
				"#" };
		Object[] row = new Object[8];
		mdlProdutosVda.setColumnIdentifiers(colunn);
		tblProdutosVenda.setModel(mdlProdutosVda);
		tblProdutosVenda.getColumnModel().getColumn(0).setMaxWidth(80);
		tblProdutosVenda.getColumnModel().getColumn(1).setMaxWidth(600);
		tblProdutosVenda.getColumnModel().getColumn(2).setMaxWidth(80);
		tblProdutosVenda.getColumnModel().getColumn(3).setMaxWidth(50);
		tblProdutosVenda.getColumnModel().getColumn(4).setMaxWidth(400);
		tblProdutosVenda.getColumnModel().getColumn(5).setMaxWidth(125);
		tblProdutosVenda.getColumnModel().getColumn(6).setMaxWidth(140);
		tblProdutosVenda.getColumnModel().getColumn(7).setMaxWidth(20);
		tblProdutosVenda.getColumnModel().getColumn(7).setCellRenderer(centralizado);
		tblProdutosVenda.getColumnModel().getColumn(5).setCellRenderer(direita);
		tblProdutosVenda.getColumnModel().getColumn(6).setCellRenderer(direita);

		// tblProdutosVenda.getColumnModel().getColumn(1).setCellRenderer(backgroundVerde);

		JScrollBar scrollBar = new JScrollBar();
		scrollPane.setRowHeaderView(scrollBar);

		textSelQtdItem = new JTextField();
		textSelQtdItem.setBackground(new Color(225, 225, 225));
		textSelQtdItem.setBounds(557, 207, 54, 20);
		panel.add(textSelQtdItem);
		textSelQtdItem.setColumns(10);

		JButton btnGravar = new JButton("Gravar");
		btnGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (validaDados() == false) {
					return;
				}

				ArrayList<ItemPedido> listaItensPedido = new ArrayList<>();

				pedido.setData_pedido(LocalDate.now());
				pedido.setClientes_cod_cliente(textCodCliente.getText());
				pedido.setCondicao_pagamento_pedido(comboBoxCondicaoPagamento.getSelectedItem().toString());
				pedido.setTipo_pedido(pedido.getTipo_pedido());
				int contador = mdlProdutosVda.getRowCount();

				for (int teste = 0; teste < contador; teste++) {

					ItemPedido itemPedido = new ItemPedido();

					itemPedido.setProdutos_cod_produto(mdlProdutosVda.getValueAt(teste, 0).toString());
					itemPedido.setQuantidade_item(mdlProdutosVda.getValueAt(teste, 2).toString());

					listaItensPedido.add(itemPedido);
				}

				pedidodao.inserirPedido(pedido, listaItensPedido);

				String codPedido = pedidodao.buscaCodigoUltimoPedido();

				if (pedido.getTipo_pedido().equals("O")) {
					JOptionPane.showInternalMessageDialog(null,
							"Orçamento [" + codPedido + "] cadastrado/alterado com sucesso!");
				}

				// Deleta orçamento
				if (orcamentoRecuperado) {
					String ultimoPedido = pedidodao.buscaCodigoUltimoPedido();
					System.out.println("Código do orcamento: " + codigoOrcamentoBase);
					pedidodao.alteraOrcamentoItens(codigoOrcamentoBase, ultimoPedido);
					pedido.setCod_pedido(ultimoPedido);
					pedidodao.alteraOrcamentoPedido(pedido, codigoOrcamentoBase);

					if (mdlProdutosVda.getRowCount() == 0) {
						pedidodao.excluirOrcamento(codigoOrcamentoBase);
						JOptionPane.showInternalMessageDialog(null,
								"Orcamento [" + codigoOrcamentoBase + "] zero itens foi excluído.");
					}

				}

				// Limpeza de todos os dados e variáveis após gravar o pedido
				limpaCampos();

				// Chamada de Cupom de Venda
				if (pedido.getTipo_pedido().equals("P")) {
					frmPrincipal frame = new frmPrincipal();
					Integer cod_pedido = pedidodao.listarUltimoPedido();
					frame.relatorioComprovanteFiscal(cod_pedido);
				}
				carregaComboBoxOrcamento();

			}

		});
		btnGravar.setBounds(258, 421, 89, 23);
		panel.add(btnGravar);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				limpaCampos();
			}
		});
		btnLimpar.setBounds(381, 421, 89, 23);
		panel.add(btnLimpar);

		rdbtnPedido.setBounds(293, 7, 64, 23);
		panel.add(rdbtnPedido);

		rdbtnOrcamento.setBounds(359, 7, 98, 23);
		panel.add(rdbtnOrcamento);

		JLabel lblProduto = new JLabel("Nome Produto:");
		lblProduto.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProduto.setBounds(23, 206, 94, 20);
		panel.add(lblProduto);

		textSelNomeProduto = new JTextField();
		textSelNomeProduto.setEditable(false);
		textSelNomeProduto.setColumns(10);
		textSelNomeProduto.setBackground(new Color(225, 225, 225));
		textSelNomeProduto.setBounds(127, 206, 330, 20);
		panel.add(textSelNomeProduto);

		JLabel lblQtdItens = new JLabel("Qtd. Itens:");
		lblQtdItens.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblQtdItens.setBounds(23, 388, 94, 20);
		panel.add(lblQtdItens);

		textQtdItens = new JTextField();
		textQtdItens.setColumns(10);
		textQtdItens.setBackground(new Color(225, 225, 225));
		textQtdItens.setBounds(99, 388, 46, 20);
		panel.add(textQtdItens);

		JLabel lblValorTotal = new JLabel("Valor Total:");
		lblValorTotal.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblValorTotal.setBounds(546, 388, 80, 20);
		panel.add(lblValorTotal);

		textValorTotal = new JTextField();
		textValorTotal.setAlignmentX(Component.RIGHT_ALIGNMENT);
		textValorTotal.setColumns(10);
		textValorTotal.setBackground(new Color(225, 225, 225));
		textValorTotal.setBounds(627, 389, 89, 20);
		panel.add(textValorTotal);

		JLabel lblCodigo = new JLabel("Código:");
		lblCodigo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCodigo.setBounds(557, 41, 54, 20);
		panel.add(lblCodigo);

		textCodCliente = new JTextField();
		textCodCliente.setEditable(false);
		textCodCliente.setColumns(10);
		textCodCliente.setBackground(new Color(225, 225, 225));
		textCodCliente.setBounds(608, 41, 46, 20);
		panel.add(textCodCliente);

		JButton btnPesquisaCliente = new JButton("");
		btnPesquisaCliente.setVerticalAlignment(SwingConstants.BOTTOM);
		btnPesquisaCliente.setIcon(
				new ImageIcon(Pedidos.class.getResource("/Icones/lupa.png")));
		btnPesquisaCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ArrayList<Cliente> cliente = new ArrayList<>();
				ClienteDao clienteDao = new ClienteDao();

				String cpf = JOptionPane.showInputDialog("Informe o CPF do Cliente: ");
				cliente = clienteDao.listarClientePorCpf(cpf);

				for (Cliente contador : cliente) {
					textCodCliente.setText(contador.getCod_cliente());
					textNomeCliente.setText(contador.getNome_cliente());
					textCPFCliente.setText(contador.getCpf_cliente());
				}
			}
		});
		btnPesquisaCliente.setBounds(672, 41, 31, 30);
		panel.add(btnPesquisaCliente);

		JButton btnInserir = new JButton("+");
		btnInserir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				System.out.println("Tipo do pedido: " + pedido.getTipo_pedido());

				String itemSelecionado = textSelCodProduto.getText();
				String qtdeSelecionada = textSelQtdItem.getText();

				if (itemSelecionado.equals("")) {
					JOptionPane.showInternalMessageDialog(null, "Nenhum produto selecionado. Verifique!");
					textSelQtdItem.setText("");
					return;
				}

				if (qtdeSelecionada.equals("")) {
					JOptionPane.showInternalMessageDialog(null, "Quantidade  é obrigatória.");
					textSelQtdItem.setText("");
					textSelQtdItem.requestFocus();
					return;
				}

				if (!ValidaEntrada.isNumero(qtdeSelecionada)) {
					JOptionPane.showInternalMessageDialog(null, "Quantidade, somente números (inteiro positivo).");
					textSelQtdItem.setText("");
					textSelQtdItem.requestFocus();
					return;
				}

				if (!pedidodao.verificaSaldoEstoque(itemSelecionado, qtdeSelecionada)) {
					JOptionPane.showInternalMessageDialog(null, "Quantidade informada indisponível no estoque!");
					textSelQtdItem.setText("");
					textSelQtdItem.requestFocus();
					return;
				}

				int nrolinhas = mdlProdutosVda.getRowCount();

				for (int i = 0; i < nrolinhas; i++) {

					if ((mdlProdutosVda.getValueAt(i, 0).toString()).equals(itemSelecionado)) {
						JOptionPane.showInternalMessageDialog(null, "Item já selecionado para o orçamento/pedido.");
						textSelQtdItem.setText("");
						textSelQtdItem.requestFocus();
						textSelCodProduto.setText("");
						textSelNomeProduto.setText("");
						textSelQtdItem.setText("");
						textSelValorVenda.setText("");
						textSelCodMarca.setText("");
						textSelDesMarca.setText("");
						return;
					}
				}

				int qtdItem = Integer.parseInt(textSelQtdItem.getText());
				// Double valorVenda = Double.parseDouble(textSelValorVenda.getText());
				ProdutoDao produtodao = new ProdutoDao();
				Double valorVenda = Double.parseDouble(produtodao.buscaPrecoVenda(textSelCodProduto.getText()));
				Double valorTotalItem = (qtdItem * valorVenda);

				row[0] = textSelCodProduto.getText();
				row[1] = textSelNomeProduto.getText();
				row[2] = textSelQtdItem.getText();
				row[3] = textSelCodMarca.getText();
				row[4] = textSelDesMarca.getText();
				row[5] = FormataDecimal.duasCasas(produtodao.buscaPrecoVenda(textSelCodProduto.getText()));
				row[6] = FormataDecimal.duasCasas(String.valueOf(valorTotalItem));

				mdlProdutosVda.addRow(row);

				valorTotalPedido += valorTotalItem;

				textValorTotal.setText(FormataDecimal.duasCasas(String.valueOf(valorTotalPedido)));

				textQtdItens.setText(String.valueOf(nrolinhas + 1));

				textSelCodProduto.setText("");
				textSelNomeProduto.setText("");
				textSelQtdItem.setText("");
				textSelValorVenda.setText("");
				textSelCodMarca.setText("");
				textSelDesMarca.setText("");
			}
		});
		btnInserir.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnInserir.setBounds(672, 206, 44, 23);
		panel.add(btnInserir);

		JButton btnRemover = new JButton("-");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String itemSelecionado = textSelCodProduto.getText();

				int nrolinhas = mdlProdutosVda.getRowCount();

				if (nrolinhas <= 0) {
					JOptionPane.showInternalMessageDialog(null, "Não existe item para ser excluído. Verifique!");
					textSelQtdItem.setText("");
					return;
				}

				if (itemSelecionado.equals("")) {
					JOptionPane.showInternalMessageDialog(null, "Nenhum produto selecionado. Verifique!");
					textSelQtdItem.setText("");
					return;
				}

				int qtdItem = Integer.parseInt(textSelQtdItem.getText());
				Double valorVenda = Double.parseDouble(produtodao.buscaPrecoVenda(textSelCodProduto.getText()));
				for (int i = 0; i < nrolinhas; i++) {
					if ((mdlProdutosVda.getValueAt(i, 0).toString()).equals(itemSelecionado)) {
						valorTotalPedido -= valorVenda * qtdItem;
						textValorTotal.setText(FormataDecimal.duasCasas(String.valueOf(valorTotalPedido)));
						System.out.println(valorTotalPedido);
						mdlProdutosVda.removeRow(i);
						break;
					}
				}

				textValorTotal.setText(String.valueOf(FormataDecimal.duasCasas(valorTotalPedido.toString())));

				textQtdItens.setText(String.valueOf(nrolinhas - 1));
				textSelCodProduto.setText("");
				textSelNomeProduto.setText("");
				textSelQtdItem.setText("");
				textSelValorVenda.setText("");
				textSelCodMarca.setText("");
				textSelDesMarca.setText("");
				textSelQtdItem.setEditable(true);
			}
		});
		btnRemover.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnRemover.setBounds(618, 206, 44, 23);
		panel.add(btnRemover);

		JLabel lblCodProduto = new JLabel("Cod.Produto:");
		lblCodProduto.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCodProduto.setBounds(23, 236, 94, 20);
		panel.add(lblCodProduto);

		JLabel lblValorUnitrio = new JLabel("Valor Venda:");
		lblValorUnitrio.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblValorUnitrio.setBounds(185, 236, 94, 20);
		panel.add(lblValorUnitrio);

		textSelValorVenda = new JTextField();
		textSelValorVenda.setEditable(false);
		textSelValorVenda.setColumns(10);
		textSelValorVenda.setBackground(new Color(225, 225, 225));
		textSelValorVenda.setBounds(267, 236, 94, 20);
		panel.add(textSelValorVenda);

		JLabel lblMarca = new JLabel("Des.Marca:");
		lblMarca.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMarca.setBounds(498, 236, 67, 20);
		panel.add(lblMarca);

		textSelDesMarca = new JTextField();
		textSelDesMarca.setEditable(false);
		textSelDesMarca.setColumns(10);
		textSelDesMarca.setBackground(new Color(225, 225, 225));
		textSelDesMarca.setBounds(567, 236, 149, 20);
		panel.add(textSelDesMarca);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(23, 106, 693, 96);
		panel.add(scrollPane_1);

		tblProdutosSelecao = new JTable();
		scrollPane_1.setViewportView(tblProdutosSelecao);
		tblProdutosSelecao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int contador1 = tblProdutosSelecao.getSelectedRow();
				int quantidadeItemDisponível = Integer.parseInt(mdlProdutosSel.getValueAt(contador1, 2).toString());

				if (quantidadeItemDisponível <= 0) {
					JOptionPane.showInternalMessageDialog(null, "Item com saldo zero no estoque.");
					return;
				}

				textSelQtdItem.setEditable(true);
				textSelCodProduto.setText(mdlProdutosSel.getValueAt(contador1, 0).toString());
				textSelNomeProduto.setText(mdlProdutosSel.getValueAt(contador1, 1).toString());
				textSelCodMarca.setText(mdlProdutosSel.getValueAt(contador1, 3).toString());
				textSelQtdItem.setText("");
				textSelDesMarca.setText(mdlProdutosSel.getValueAt(contador1, 4).toString());
				textSelValorVenda.setText(mdlProdutosSel.getValueAt(contador1, 5).toString());
				textSelQtdItem.requestFocus();

			}
		});

		mdlProdutosSel = new DefaultTableModel();
		Object[] colunn1 = { "Cod.Produto", "Des.Produto", "Quantidade", "Marca", "Des.Marca", "Vlr.Venda" };
		Object[] row1 = new Object[6];
		mdlProdutosSel.setColumnIdentifiers(colunn1);
		tblProdutosSelecao.setModel(mdlProdutosSel);
		tblProdutosSelecao.getColumnModel().getColumn(0).setMaxWidth(100);
		tblProdutosSelecao.getColumnModel().getColumn(1).setMaxWidth(600);
		tblProdutosSelecao.getColumnModel().getColumn(2).setMaxWidth(80);
		tblProdutosSelecao.getColumnModel().getColumn(3).setMaxWidth(50);
		tblProdutosSelecao.getColumnModel().getColumn(4).setMaxWidth(400);
		tblProdutosSelecao.getColumnModel().getColumn(5).setMaxWidth(150);

		tblProdutosSelecao.getColumnModel().getColumn(5).setCellRenderer(direita);

		JScrollBar scrollBar_1 = new JScrollBar();
		scrollPane_1.setRowHeaderView(scrollBar_1);

		JLabel lblCodMarca = new JLabel("Cod.Marca:");
		lblCodMarca.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCodMarca.setBounds(369, 236, 82, 20);
		panel.add(lblCodMarca);

		textSelCodMarca = new JTextField();
		textSelCodMarca.setEditable(false);
		textSelCodMarca.setColumns(10);
		textSelCodMarca.setBackground(new Color(225, 225, 225));
		textSelCodMarca.setBounds(440, 236, 54, 20);
		panel.add(textSelCodMarca);

		JButton btnBuscaProdutos = new JButton("Pesquisar Produtos");
		btnBuscaProdutos.setMargin(new Insets(2, 3, 2, 3));
		btnBuscaProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Produto();
				// ProdutoDao produtodao = new ProdutoDao();

				if (mdlProdutosSel.getRowCount() != 0) {
					mdlProdutosSel.setRowCount(0);
				}
				String nome = JOptionPane.showInputDialog("Informe o nome do Produto: ");

				ArrayList<Produto> listaDeProdutos = new ArrayList<>();
				listaDeProdutos = produtodao.listarProdutoPorNome(nome);
				String valorVenda = "";

				for (Produto contador : listaDeProdutos) {
					valorVenda = FormataDecimal.duasCasas(contador.getValor_venda_produto());
					row1[0] = contador.getCod_produto();
					row1[1] = contador.getNome_produto();
					row1[3] = contador.getCod_marca_produto();
					row1[2] = contador.getQuantidade_produto();
					row1[4] = contador.getNome_marca_produto();
					// row1[5] = contador.getValor_venda_produto();
					row1[5] = valorVenda;
					mdlProdutosSel.addRow(row1);
				}
			}

		});
		btnBuscaProdutos.setBounds(23, 72, 136, 23);
		panel.add(btnBuscaProdutos);
		cmbBoxOrcamento.setVisible(false);
		cmbBoxOrcamento.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean mudancaPreco = false;
				btnExcluiOrcamento.setVisible(false);

				if (e.getStateChange() == ItemEvent.SELECTED) {

					String codigoPedido = cmbBoxOrcamento.getSelectedItem().toString();

					if (!codigoPedido.equals("")) {
						btnExcluiOrcamento.setVisible(true);

						if (JOptionPane.showConfirmDialog(null, "Deseja recuperar Orçamento?", "SIM",
								JOptionPane.YES_NO_OPTION) == 0) {
							orcamentoRecuperado = true;

							ArrayList<ListaPedido> Pedido = new ArrayList();
							Pedido = pedidodao.listarPedidoPorCodigo(Integer.parseInt(codigoPedido));

							codigoOrcamentoBase = Pedido.get(0).getCod_pedido();
							comboBoxCondicaoPagamento.setSelectedItem(Pedido.get(0).getCondicao_pagamento_pedido());
							textCodCliente.setText(Pedido.get(0).getCod_cliente());
							textNomeCliente.setText(Pedido.get(0).getNome_cliente());
							textCPFCliente.setText(Pedido.get(0).getCpf_cliente());

							int quantidadeTotalItens = 0;

							for (ListaPedido pedido : Pedido) {

								codigoOrcamentoBase = pedido.getCod_pedido();

								int qtdItem = Integer.parseInt(pedido.getQuantidade_item());

								String valorVendaOrcamentoSt = pedido.getPreco_total_item().toString();

								Double valorVendaOrcamento = (Double.parseDouble(valorVendaOrcamentoSt) / qtdItem);
								Double valorTotalItem = (qtdItem * valorVendaOrcamento);

								String codigoProduto = pedido.getCod_produto().toString();
								Double precoVendaProduto = Double
										.parseDouble(produtodao.buscaPrecoVenda(codigoProduto));

								// Verifica alteração do preço de venda
								if ((valorVendaOrcamento - precoVendaProduto) != 0) {
									row[7] = "*";
									mudancaPreco = true;
									// System.out.println("Valor da operação: " + (valorVendaOrcamento -
									// precoVendaProduto));
								} else {
									row[7] = "";
								}

								row[0] = pedido.getCod_produto();
								row[1] = pedido.getNome_produto();
								row[2] = qtdItem;
								row[3] = pedido.getCod_marca();
								row[4] = pedido.getDescricao_marca();
								row[5] = FormataDecimal.duasCasas(valorVendaOrcamento.toString());
								row[6] = FormataDecimal.duasCasas(String.valueOf(valorTotalItem));

								valorTotalPedido += valorTotalItem;

								textValorTotal.setText(FormataDecimal.duasCasas(String.valueOf(valorTotalPedido)));

								mdlProdutosVda.addRow(row);

								quantidadeTotalItens = mdlProdutosVda.getRowCount();

								textQtdItens.setText(String.valueOf(quantidadeTotalItens));

							}

						}

						if (mudancaPreco) {
							JOptionPane.showMessageDialog(null,
									"Preço de venda do produtos sinalizados com '*' foi alterado no cadastro. Verifique!");
						}
					}
				}
			}
		});

		cmbBoxOrcamento.setBounds(477, 10, 67, 18);
		panel.add(cmbBoxOrcamento);

		btnExcluiOrcamento.setVisible(false);
		btnExcluiOrcamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String codigoPedido = cmbBoxOrcamento.getSelectedItem().toString();
				if (JOptionPane.showConfirmDialog(null, "Confirma Exclusão do Orçamento?", "SIM",
						JOptionPane.YES_NO_OPTION) == 0) {
					if (pedidodao.excluirOrcamento(codigoPedido)) {
						JOptionPane.showInternalMessageDialog(null, "Orçamento [" + codigoPedido + "] excluído.");
						limpaCampos();
					}
				}
				carregaComboBoxOrcamento();
			}
		});
		btnExcluiOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnExcluiOrcamento.setMargin(new Insets(2, 3, 2, 3));
		btnExcluiOrcamento.setBounds(588, 8, 128, 23);
		panel.add(btnExcluiOrcamento);

	}

	public boolean validaDados() {

		String condicaoPagamento = comboBoxCondicaoPagamento.getSelectedItem().toString();

		if (condicaoPagamento.equals("")) {
			JOptionPane.showInternalMessageDialog(null, "Condição de Pagamento é obrigatória.");
			comboBoxCondicaoPagamento.requestFocus();
			return (false);
		}

		if (!rdbtnPedido.isSelected()) {
			if (!rdbtnOrcamento.isSelected()) {
				JOptionPane.showInternalMessageDialog(null, "Selecione 'Orçamento' ou 'Pedido'");
				return (false);
			}
		}

		if (textCPFCliente.getText().equals("")) {
			JOptionPane.showInternalMessageDialog(null, "Nenhum cliente informado.");
			return (false);
		}

		if ((textQtdItens.getText().equals("")) || (textValorTotal.getText().equals(""))) {
			JOptionPane.showInternalMessageDialog(null, "Pedido não contém Itens ou Valor Total válidos");
			return (false);
		}

		return (true);
	}

	void limpaCampos() {

		textCPFCliente.setText("");
		textNomeCliente.setText("");
		textQtdItens.setText("");
		textValorTotal.setText("");
		textSelCodProduto.setText("");
		textSelQtdItem.setText("");
		textSelNomeProduto.setText("");
		textSelValorVenda.setText("");
		textSelCodMarca.setText("");
		textSelDesMarca.setText("");
		comboBoxCondicaoPagamento.setSelectedIndex(-1);
		rdbtnOrcamento.setSelected(false);
		rdbtnPedido.setSelected(false);
		textCodCliente.setText("");
		codigoOrcamentoBase = "";
		orcamentoRecuperado = false;

		cmbBoxOrcamento.setSelectedItem("");
		comboBoxCondicaoPagamento.setSelectedItem("");

		mdlProdutosSel.setRowCount(0);
		mdlProdutosVda.setRowCount(0);

		valorTotalPedido = 0.0;

	}

	void limpaCampos(String PedidoOrcamento) {

		if (PedidoOrcamento.equals("P")) {
			rdbtnOrcamento.setSelected(false);
		}
		if (PedidoOrcamento.equals("O")) {
			rdbtnPedido.setSelected(false);
		}

		textCodCliente.setText("");
		textCPFCliente.setText("");
		textNomeCliente.setText("");
		textQtdItens.setText("");
		textValorTotal.setText("");
		textSelCodProduto.setText("");
		textSelQtdItem.setText("");
		textSelNomeProduto.setText("");
		textSelValorVenda.setText("");
		textSelCodMarca.setText("");
		textSelDesMarca.setText("");
		comboBoxCondicaoPagamento.setSelectedIndex(-1);
		comboBoxCondicaoPagamento.setSelectedItem("");
		codigoOrcamentoBase = "";
		orcamentoRecuperado = false;

		mdlProdutosSel.setRowCount(0);
		mdlProdutosVda.setRowCount(0);

		valorTotalPedido = 0.0;

	}

	public void carregaComboBoxOrcamento() {
		pedidodao.listarTodosOrcamentos();

		ArrayList<Integer> listaDeOrcamentos = new ArrayList<>();
		listaDeOrcamentos = pedidodao.listarTodosOrcamentos();

		cmbBoxOrcamento.removeAllItems();

		cmbBoxOrcamento.addItem("");

		for (Integer contador : listaDeOrcamentos) {
			cmbBoxOrcamento.addItem(contador);
		}

	}
}
