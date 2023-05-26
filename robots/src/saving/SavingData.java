package saving;

import saving.states.LocaleState;
import saving.states.FramesState;

public class SavingData {
    private final FramesState windowState = new FramesState().getInstance();
    private final LocaleState localeState = new LocaleState().getInstance();
    private static final SavingData savingData = new SavingData();

    public SavingData() {
    }

    public FramesState windowState() {
        return windowState;
    }

    public LocaleState localeState() {
        return localeState;
    }

    public static SavingData getInstance() {
        return savingData;
    }
}
