package ru.unn.agile.Range.view.legacy;

import ru.unn.agile.Range.ViewModel.legacy.ViewModel;
import ru.unn.agile.Range.infrastructure_lab3_legacy.TxtLogger;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;

public final class RangeCalculator {

    public static void main(final String[] args) {
        JFrame frame = new JFrame("RangeCalculator");

        TxtLogger logger = new TxtLogger("./RangeCalculator.log");
        frame.setContentPane(new RangeCalculator(new ViewModel(logger)).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private RangeCalculator(final ViewModel viewModel) {
        this.viewModel = viewModel;

        backBind();

        loadListOfOperations();

        cbOperation.addActionListener(
                ae -> {
                    bind();
                    backBind();
                });

        btnCalculate.addActionListener(
                ae -> {
                    bind();
                    viewModel.calculate();
                    backBind();
                }
        );

        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent de) {
                bind();
                backBind();
            }

            @Override
            public void removeUpdate(final DocumentEvent de) {
                bind();
                backBind();
            }

            @Override
            public void changedUpdate(final DocumentEvent de) {
                bind();
                backBind();
            }
        };
        txtInputRange.getDocument().addDocumentListener(documentListener);
        txtInputArgument.getDocument().addDocumentListener(documentListener);
    }

    private void loadListOfOperations() {
        ViewModel.Operation[] operations = ViewModel.Operation.values();
        cbOperation.setModel(new JComboBox<>(operations).getModel());
    }

    private void bind() {
        viewModel.setInputRange(txtInputRange.getText());
        viewModel.setInputArgument(txtInputArgument.getText());
        viewModel.setOperation((ViewModel.Operation) cbOperation.getSelectedItem());
    }

    private void backBind() {
        btnCalculate.setEnabled(viewModel.isCalculateButtonEnabled());
        txtInputArgument.setEnabled(viewModel.isInputArgumentTextFieldEnabled());
        txtResult.setText(viewModel.getResult());
        txtMessage.setText(viewModel.getMessageText());

        List<String> log = viewModel.getLog();
        String[] items = log.toArray(new String[log.size()]);
        lstLog.setListData(items);
    }

    private JTextField txtInputRange;
    private JTextField txtInputArgument;
    private JTextField txtResult;
    private JButton btnCalculate;
    private JComboBox<ViewModel.Operation> cbOperation;
    private JTextArea txtMessage;
    private JPanel mainPanel;
    private JList<String> lstLog;
    private ViewModel viewModel;
}
