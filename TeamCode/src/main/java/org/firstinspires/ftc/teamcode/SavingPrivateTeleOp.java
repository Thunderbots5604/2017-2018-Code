package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="SavingPrivateTeleOp", group="TeleOp")
public class SavingPrivateTeleOp extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        DcMotor leftMotorFront = null;
        DcMotor leftMotorBack = null;

        DcMotor rightMotorFront = null;
        DcMotor rightMotorBack = null;

        DcMotor arm = null;
        DcMotor push = null;
        DcMotor armtwo = null;

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");

        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

        arm = hardwareMap.dcMotor.get("arm");

        armtwo = hardwareMap.dcMotor.get("armtwo");

        push = hardwareMap.dcMotor.get("push");

        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);
        leftMotorFront.setDirection(DcMotor.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotor.Direction.REVERSE);

        armtwo.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            //Stuff to display for Telemetry
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Right Trigger", gamepad1.right_trigger);
            telemetry.addData("Left Front", "Power: " + leftMotorFront.getPower());
            telemetry.addData("Left Back", "Power: " + leftMotorBack.getPower());
            telemetry.addData("Right Front", "Power: " + rightMotorFront.getPower());
            telemetry.addData("Right Back", "Power: " + rightMotorBack.getPower());
            telemetry.update();

            //Forward and backward moving method
            if(gamepad1.left_stick_y != 0 && gamepad1.left_stick_x == 0 && gamepad1.right_stick_x == 0 && gamepad1.right_trigger == 0) {
                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y);

                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y);
            }
            else if(gamepad1.left_stick_y < 0 && gamepad1.left_stick_x > 0 && gamepad1.right_stick_x == 0 && gamepad1.right_trigger == 0) {
                rightMotorFront.setPower(-gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));
                rightMotorBack.setPower(-gamepad1.left_stick_y);

                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
            }
            else if (gamepad1.left_stick_y == 0 && gamepad1.left_stick_x != 0 && gamepad1.right_stick_x == 0 && gamepad1.right_trigger == 0) {
                leftMotorFront.setPower(-gamepad1.left_stick_x);
                rightMotorFront.setPower(-gamepad1.left_stick_x);

                leftMotorBack.setPower(gamepad1.left_stick_x);
                rightMotorBack.setPower(gamepad1.left_stick_x);
            }
            else if(gamepad1.left_stick_y > 0 && gamepad1.left_stick_x > 0 && gamepad1.right_stick_x == 0 && gamepad1.right_trigger == 0) {
                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));

                leftMotorFront.setPower(gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));
                leftMotorBack.setPower(gamepad1.left_stick_y);
            }
            else if(gamepad1.left_stick_y > 0 && gamepad1.left_stick_x < 0 && gamepad1.right_stick_x == 0 && gamepad1.right_trigger == 0) {
                rightMotorFront.setPower(-gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
                rightMotorBack.setPower(-gamepad1.left_stick_y);

                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));
            }
            else if(gamepad1.left_stick_y < 0 && gamepad1.left_stick_x < 0 && gamepad1.right_stick_x == 0 && gamepad1.right_trigger == 0) {
                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));

                leftMotorFront.setPower(gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
                leftMotorBack.setPower(gamepad1.left_stick_y);
            }
            else if (gamepad1.left_stick_y !=0  && gamepad1.right_stick_x > 0 && gamepad1.right_trigger == 0) {
                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x < 0 && gamepad1.right_trigger == 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y);
            }
            else if(gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                leftMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));

                rightMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                rightMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
            }
            else if(gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                leftMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));

                rightMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                rightMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
            }
            else if (gamepad1.left_stick_y != 0 && gamepad1.left_stick_x == 0 && gamepad1.right_stick_x == 0 && gamepad1.right_trigger != 0) {
                rightMotorFront.setPower(gamepad1.left_stick_y);
                rightMotorBack.setPower(gamepad1.left_stick_y);

                leftMotorFront.setPower(-gamepad1.left_stick_y);
                leftMotorBack.setPower(-gamepad1.left_stick_y);
            }
            else if(gamepad1.left_stick_y < 0 && gamepad1.left_stick_x > 0 && gamepad1.right_stick_x == 0 && gamepad1.right_trigger != 0) {

                rightMotorFront.setPower(-gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
                rightMotorBack.setPower(-gamepad1.left_stick_y);

                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));
            }
            else if (gamepad1.left_stick_y == 0 && gamepad1.left_stick_x != 0 && gamepad1.right_stick_x == 0 && gamepad1.right_trigger != 0) {

                leftMotorFront.setPower(gamepad1.left_stick_x);
                rightMotorFront.setPower(gamepad1.left_stick_x);

                leftMotorBack.setPower(-gamepad1.left_stick_x);
                rightMotorBack.setPower(-gamepad1.left_stick_x);
            }
            else if(gamepad1.left_stick_y > 0 && gamepad1.left_stick_x > 0 && gamepad1.right_stick_x == 0 && gamepad1.right_trigger != 0) {
                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));

                leftMotorFront.setPower(gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
                leftMotorBack.setPower(gamepad1.left_stick_y);

            }
            else if(gamepad1.left_stick_y > 0 && gamepad1.left_stick_x < 0 && gamepad1.right_stick_x == 0 && gamepad1.right_trigger != 0) {

                rightMotorFront.setPower(-gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));
                rightMotorBack.setPower(-gamepad1.left_stick_y);

                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
            }
            else if(gamepad1.left_stick_y < 0 && gamepad1.left_stick_x < 0 && gamepad1.right_stick_x == 0 && gamepad1.right_trigger != 0) {

                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));

                leftMotorFront.setPower(gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));
                leftMotorBack.setPower(gamepad1.left_stick_y);
            }
            else if (gamepad1.left_stick_y !=0  && gamepad1.right_stick_x > 0 && gamepad1.right_trigger != 0) {

                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y);
            }
            else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x < 0 && gamepad1.right_trigger != 0) {

                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }

        }
    }
}
