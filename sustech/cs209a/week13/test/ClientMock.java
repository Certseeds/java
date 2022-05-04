// SPDX-License-Identifier: AGPL-3.0-or-later


public interface ClientMock {
    String sendNameCommand(String s);

    String sendGradeCommand(String s);

    String sendTopCommand(String s);

    String sendUndefinedCommand(String s);
}
