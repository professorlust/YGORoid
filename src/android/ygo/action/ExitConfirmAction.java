package android.ygo.action;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.ygo.R;
import android.ygo.op.Operation;
import android.ygo.utils.Utils;

public class ExitConfirmAction extends BaseAction {
    public ExitConfirmAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        AlertDialog dialog = new AlertDialog.Builder(Utils.getContext())
                .setTitle(Utils.s(R.string.QUIT))
                .setPositiveButton(Utils.s(R.string.CONFIRM_YES), new OnExitClickListener("OK"))
                .setNegativeButton(Utils.s(R.string.CONFIRM_NO), new OnExitClickListener("Cancel"))
                .create();
        dialog.show();
    }

    private static class OnExitClickListener implements DialogInterface.OnClickListener {
        private String button;

        public OnExitClickListener(String button) {
            this.button = button;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if ("OK".equals(button)) {
                Utils.getContext().stopService();

                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        }
    }
}
