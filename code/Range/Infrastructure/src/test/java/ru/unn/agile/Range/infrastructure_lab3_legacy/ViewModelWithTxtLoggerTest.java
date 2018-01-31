package ru.unn.agile.Range.infrastructure_lab3_legacy;

import ru.unn.agile.Range.ViewModel.legacy.ViewModel;
import ru.unn.agile.Range.ViewModel.legacy.ViewModelTests;

public class ViewModelWithTxtLoggerTest extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
            new TxtLogger("./ViewModelWithTxtLoggerTests-lab3-legacy.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
