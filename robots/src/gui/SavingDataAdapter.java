package gui;


import saving.SavingData;

import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public abstract class SavingDataAdapter implements InternalFrameListener, ComponentListener {
    private final SavingData savingData;

    protected SavingDataAdapter(SavingData savingData) {
        this.savingData = savingData;
    }


    @Override
    public void componentResized(ComponentEvent e) {
        savingData.windowState().updateSize(e);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        savingData.windowState().updateLocation(e);
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
        savingData.windowState().updateStateToOpened(e);
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
        savingData.windowState().updateStateToClosed(e);
    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {
        savingData.windowState().updateViewToIconified(e);
    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {
        savingData.windowState().updateViewToDeiconified(e);
    }


    @Override
    public void internalFrameActivated(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {
    }
}
