
namespace GTKClient.forms
{
    partial class Inscriere
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.textBoxNume = new System.Windows.Forms.TextBox();
            this.textBoxVarsta = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.dataGridViewProbeParticipant = new System.Windows.Forms.DataGridView();
            this.dataGridViewProbe = new System.Windows.Forms.DataGridView();
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.label4 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewProbeParticipant)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewProbe)).BeginInit();
            this.SuspendLayout();
            // 
            // textBoxNume
            // 
            this.textBoxNume.Location = new System.Drawing.Point(144, 80);
            this.textBoxNume.Name = "textBoxNume";
            this.textBoxNume.Size = new System.Drawing.Size(100, 22);
            this.textBoxNume.TabIndex = 0;
            // 
            // textBoxVarsta
            // 
            this.textBoxVarsta.Location = new System.Drawing.Point(144, 123);
            this.textBoxVarsta.Name = "textBoxVarsta";
            this.textBoxVarsta.Size = new System.Drawing.Size(100, 22);
            this.textBoxVarsta.TabIndex = 1;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(54, 80);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(45, 17);
            this.label1.TabIndex = 2;
            this.label1.Text = "Nume";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(54, 123);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(49, 17);
            this.label2.TabIndex = 3;
            this.label2.Text = "Varsta";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(369, 9);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(62, 17);
            this.label3.TabIndex = 4;
            this.label3.Text = "Inscriere";
            // 
            // dataGridViewProbeParticipant
            // 
            this.dataGridViewProbeParticipant.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewProbeParticipant.Location = new System.Drawing.Point(74, 198);
            this.dataGridViewProbeParticipant.Name = "dataGridViewProbeParticipant";
            this.dataGridViewProbeParticipant.RowHeadersWidth = 51;
            this.dataGridViewProbeParticipant.RowTemplate.Height = 24;
            this.dataGridViewProbeParticipant.Size = new System.Drawing.Size(240, 150);
            this.dataGridViewProbeParticipant.TabIndex = 5;
            // 
            // dataGridViewProbe
            // 
            this.dataGridViewProbe.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewProbe.Location = new System.Drawing.Point(502, 80);
            this.dataGridViewProbe.Name = "dataGridViewProbe";
            this.dataGridViewProbe.RowHeadersWidth = 51;
            this.dataGridViewProbe.RowTemplate.Height = 24;
            this.dataGridViewProbe.Size = new System.Drawing.Size(240, 150);
            this.dataGridViewProbe.TabIndex = 6;
            this.dataGridViewProbe.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.select);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(543, 288);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(144, 36);
            this.button1.TabIndex = 7;
            this.button1.Text = "Adauga Proba";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.adaugaProba);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(325, 384);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(126, 54);
            this.button2.TabIndex = 8;
            this.button2.Text = "Inscrie";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.inscrie);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(12, 260);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(46, 17);
            this.label4.TabIndex = 9;
            this.label4.Text = "Probe";
            // 
            // Inscriere
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.dataGridViewProbe);
            this.Controls.Add(this.dataGridViewProbeParticipant);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.textBoxVarsta);
            this.Controls.Add(this.textBoxNume);
            this.Name = "Inscriere";
            this.Text = "Inscriere";
            this.Load += new System.EventHandler(this.Inscriere_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewProbeParticipant)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewProbe)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox textBoxNume;
        private System.Windows.Forms.TextBox textBoxVarsta;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.DataGridView dataGridViewProbeParticipant;
        private System.Windows.Forms.DataGridView dataGridViewProbe;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Label label4;
    }
}