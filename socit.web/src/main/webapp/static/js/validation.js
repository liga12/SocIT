$(document).ready(function () {
    $("#form").each(function () {
        $(this).validate({
            rules: {
                firstName: {
                    required: true,
                    rangelength: [1, 32]
                },
                lastName: {
                    required: true,
                    rangelength: [1, 32]
                },
                email: {
                    required: true,
                    email: true,
                    rangelength: [1, 64]
                },

                login: {
                    required: true,
                    rangelength: [1, 32]
                },
                password: {
                    required: true,
                    rangelength: [8, 248]
                },
                passwordConfirmation: {
                    required: true,
                    rangelength: [8, 248],
                    equalTo: "#password"
                },
                old_password: {
                    required: true,
                    rangelength: [8, 248]
                },

                city:{
                    rangelength: [1, 64]
                }
            }, messages: {
                firstName: {
                    required: "First Name is required",
                    rangelength: "Please enter First Name between 1 and 32 characters long."
                },
                lastName: {
                    required: "Last Name is required",
                    rangelength: "Please enter Last Name between 1 and 32 characters long."
                },
                email: {
                    required: "Email is required",
                    rangelength: "Please enter Email between 1 and 64 characters long."
                },
                login: {
                    required: "Login is required",
                    rangelength: "Please enter Login between 1 and 32 characters long."
                },
                password: {
                    required: "Password is required",
                    rangelength: "Please enter Password between 8 and 248 characters long."
                },
                passwordConfirmation: {
                    required: "Password is required",
                    rangelength: "Please enter Password between 8 and 248 characters long.",
                    equalTo: "Passwords do not match"
                },
                old_password: {
                    required: "Old password is required",
                    rangelength: "Please enter Old password between 8 and 248 characters long."
                },

                city: {
                    rangelength: "Please enter City password between 1 and 64 characters long."
                }
            }
        });
    });
});