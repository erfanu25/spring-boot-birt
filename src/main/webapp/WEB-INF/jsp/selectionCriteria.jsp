<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<title>Selection Criteria</title>

<link rel="stylesheet" href="/resources/css/bootstrap.min.css">

<script src="/resources/js/jquery-3.4.1.min.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<script src="/resources/js/popper.min.js"></script>
</head>
<style>
.accProp {
	background-color: #f2f2f2;
	padding: .15rem 1.25rem;
}

.accText {
	color: #29639f;
}

.buttonProps {
	border-color: #29639f;
	background-color: #29639f;
}

.contentWrapper {
	background-color: #f5f5f5;
}
</style>
<script>
	function resetFields() {
		$('input[type=text]').val('');
		$('input[type=date]').val('');
	}
</script>
<body class="contentWrapper">

	<div class="">
		<form method="post" action="generateUserReport">
			<div class="form-row   col-md-6">&nbsp;</div>
			<div class="col-md-12" id="accordionExample">

				<div class="card">
					<div class="card-header accProp" id="headingTwo">
						<h2 class="mb-0">
							<button class="btn btn-link collapsed accText" type="button"
								data-toggle="collapse" data-target="#collapseTwo"
								aria-expanded="false" aria-controls="collapseTwo">
								Selection Criteria</button>
						</h2>
					</div>
					<div class="form-row   col-md-6">&nbsp;</div>
					<div class="form-row  col-md-6">
						<div class="col-md-4 mb-4">
							<label for="validationDefault01">User Id From: </label> <input
								type="text" name="userIdFrom" class="form-control"
								id="validationDefault01" placeholder="User Id From" value=""
								required>
						</div>
						<div class="col-md-4 mb-4">
							<label for="validationDefault02">User Id To:</label> <input
								type="text" name="userIdTo" class="form-control"
								id="validationDefault02" placeholder="User Id To" value="" required>
						</div>
					</div>
					<div class="form-row col-md-6">
						<div class="col-md-4 mb-4">
							<label for="validationDefault03">DOB From: </label> <input
								type="date" name="dobFrom" class="form-control"
								id="validationDefault03" placeholder="DOB From" required>
						</div>
						<div class="col-md-4 mb-4">
							<label for="validationDefault04">DOB To: </label> <input
								type="date" name="dobTo" class="form-control"
								id="validationDefault04" placeholder="DOB To" required>
						</div>
					</div>

					<div class="form-row  col-md-8">
						<legend class="col-form-label col-sm-2 pt-0">Report
							Format</legend>
						<div class="col-sm-10">
							<div class="form-check">
								<input class="form-check-input" type="radio" name="reportFormat"
									id="gridRadios1" value="pdf" checked> <label
									class="form-check-label" for="gridRadios1"> PDF </label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="radio" name="reportFormat"
									id="gridRadios2" value="xlsx"> <label
									class="form-check-label" for="gridRadios2"> Excel </label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="radio" name="reportFormat"
									id="gridRadios2" value="doc"> <label
									class="form-check-label" for="gridRadios2"> Word </label>
							</div>
						</div>
					</div>
					<div class="form-row   col-md-6">&nbsp;</div>
					<div class="form-row   col-md-6">
						<button class="btn btn-primary btn-group mr-2 buttonProps"
							type="submit">Generate</button>

						<button class="btn btn-primary btn-group mr-2 buttonProps"
							type="button" onclick="resetFields()">Reset</button>

					</div>
					<div class="form-row   col-md-6">&nbsp;</div>

				</div>
			</div>

		</form>
	</div>
</body>

</html>